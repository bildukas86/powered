package Extensions.AutoVoteReward;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.HashMap;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

public class VoteRewardHopzone
{
	// Configurations.
	private static String hopzoneUrl = Config.HOPZONE_SERVER_LINK;
	private static String page1Url = Config.HOPZONE_FIRST_PAGE_LINK;
	private static int voteRewardVotesDifference = Config.HOPZONE_VOTES_DIFFERENCE;
	private static int firstPageRankNeeded = Config.HOPZONE_FIRST_PAGE_RANK_NEEDED;
	private static int checkTime = 60 * 1000 * Config.HOPZONE_REWARD_CHECK_TIME;
	
	// Don't-touch variables.
	private static int lastVotes = 0;
	private static HashMap<String, Integer> playerIps = new HashMap<>();
	
	public static void updateConfigurations()
	{
		hopzoneUrl = Config.HOPZONE_SERVER_LINK;
		page1Url = Config.HOPZONE_FIRST_PAGE_LINK;
		voteRewardVotesDifference = Config.HOPZONE_VOTES_DIFFERENCE;
		firstPageRankNeeded = Config.HOPZONE_FIRST_PAGE_RANK_NEEDED;
		checkTime = 60 * 1000 * Config.HOPZONE_REWARD_CHECK_TIME;
	}
	
	public static void getInstance()
	{
		System.out.println("Hopzone - Vote reward system initialized.");
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				if (Config.ALLOW_HOPZONE_VOTE_REWARD)
				{
					reward();
				}
				else
				{
					return;
				}
			}
		}, checkTime / 2, checkTime);
	}
	
	static void reward()
	{
		int firstPageVotes = getFirstPageRankVotes();
		int currentVotes = getVotes();
		
		/*if (firstPageVotes == -1 || currentVotes == -1)
		{
			if (firstPageVotes == -1)
			{
				System.out.println("There was a problem on getting Hopzone votes from server with rank " + firstPageRankNeeded + ".");
			}
			if (currentVotes == -1)
			{
				System.out.println("There was a problem on getting Hopzone server votes.");
			}
			
			return;
		}*/
		
		if (lastVotes == 0)
		{
			lastVotes = currentVotes;
			Announcements.announceToAll("Hopzone: Vote count is " + currentVotes + ".");
			Announcements.announceToAll("Hopzone: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for reward.");
			if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
			{
				System.out.println("Server votes on hopzone: " + currentVotes);
				System.out.println("Votes needed for reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
			}
			if (firstPageVotes - lastVotes <= 0)
			{
				Announcements.announceToAll("Hopzone: We are in the top " + firstPageRankNeeded + ", so the reward will be big.");
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server is on the top " + firstPageRankNeeded + " of hopzone.");
				}
			}
			else
			{
				Announcements.announceToAll("Hopzone: We need " + (firstPageVotes - lastVotes) + " vote(s) to get to the top " + firstPageRankNeeded + " of Hopzone for big reward.");
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes needed for top " + firstPageRankNeeded + ": " + (firstPageVotes - lastVotes));
				}
			}
			return;
		}
		
		if (currentVotes >= lastVotes + voteRewardVotesDifference)
		{
			Collection<L2PcInstance> pls = L2World.getInstance().getAllPlayers().values();
			if (firstPageVotes - currentVotes <= 0)
			{
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on hopzone: " + currentVotes);
					System.out.println("Server is on the first page of hopzone.");
					System.out.println("Votes needed for next reward: " + ((currentVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.announceToAll("Hopzone: Everyone has been rewarded with big reward.");
				Announcements.announceToAll("Hopzone: Current vote count is " + currentVotes + ".");
				for (L2PcInstance p : pls)
				{
					boolean canReward = false;
					String pIp = p.getClient().getConnection().getInetAddress().getHostAddress();
					if (playerIps.containsKey(pIp))
					{
						int count = playerIps.get(pIp);
						if (count < Config.HOPZONE_DUALBOXES_ALLOWED)
						{
							playerIps.remove(pIp);
							playerIps.put(pIp, count + 1);
							canReward = true;
						}
					}
					else
					{
						canReward = true;
						playerIps.put(pIp, 1);
					}
					if (canReward)
					{
						for (int i = 0; i < Config.HOPZONE_BIG_REWARD.length; i++)
						{
							p.addItem("Vote reward.", Config.HOPZONE_BIG_REWARD[i][0], Config.HOPZONE_BIG_REWARD[i][1], p, true);
						}
					}
					else
					{
						p.sendMessage("Already " + Config.HOPZONE_DUALBOXES_ALLOWED + " character(s) of your ip have been rewarded, so this character won't be rewarded.");
					}
				}
				playerIps.clear();
			}
			else
			{
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on hopzone: " + currentVotes);
					System.out.println("Server votes needed for first page: " + (firstPageVotes - lastVotes));
					System.out.println("Votes needed for next reward: " + ((currentVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.announceToAll("Hopzone: Everyone has been rewarded with small reward.");
				Announcements.announceToAll("Hopzone: Current vote count is " + currentVotes + ".");
				Announcements.announceToAll("Hopzone: We need " + (firstPageVotes - currentVotes) + " vote(s) to get to the top " + firstPageRankNeeded + " of Hopzone for big reward.");
				for (L2PcInstance p : pls)
				{
					boolean canReward = false;
					String pIp = p.getClient().getConnection().getInetAddress().getHostAddress();
					if (playerIps.containsKey(pIp))
					{
						int count = playerIps.get(pIp);
						if (count < Config.HOPZONE_DUALBOXES_ALLOWED)
						{
							playerIps.remove(pIp);
							playerIps.put(pIp, count + 1);
							canReward = true;
						}
					}
					else
					{
						canReward = true;
						playerIps.put(pIp, 1);
					}
					if (canReward)
					{
						for (int i = 0; i < Config.HOPZONE_SMALL_REWARD.length; i++)
						{
							p.addItem("Vote reward.", Config.HOPZONE_SMALL_REWARD[i][0], Config.HOPZONE_SMALL_REWARD[i][1], p, true);
						}
					}
					else
					{
						p.sendMessage("Already " + Config.HOPZONE_DUALBOXES_ALLOWED + " character(s) of your ip have been rewarded, so this character won't be rewarded.");
					}
				}
				playerIps.clear();
			}
			
			lastVotes = currentVotes;
		}
		else
		{
			if (firstPageVotes - currentVotes <= 0)
			{
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on hopzone: " + currentVotes);
					System.out.println("Server is on the first page of hopzone.");
					System.out.println("Votes needed for next reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.announceToAll("Hopzone: Current vote count is " + currentVotes + ".");
				Announcements.announceToAll("Hopzone: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for big reward.");
			}
			else
			{
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on hopzone: " + currentVotes);
					System.out.println("Server votes needed for first page: " + (firstPageVotes - lastVotes));
					System.out.println("Votes needed for next reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.announceToAll("Hopzone: Current vote count is " + currentVotes + ".");
				Announcements.announceToAll("Hopzone: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for small reward.");
				Announcements.announceToAll("Hopzone: We need " + (firstPageVotes - currentVotes) + " vote(s) to get to the top " + firstPageRankNeeded + " of Hopzone for big reward.");
			}
		}
	}
	
	private static int getFirstPageRankVotes()
	{
		InputStreamReader isr = null;
		BufferedReader br = null;
		if(!page1Url.endsWith(".html"))
			page1Url+=".html";
		try
		{
			URLConnection con = new URL(page1Url).openConnection();
			con.addRequestProperty("User-L2Hopzone", "Mozilla/4.76");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			String line;
			int i = 0;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("<span class=\"no\">" + firstPageRankNeeded + "</span>"))
				{
					i++;
				}
				if (line.contains("<span class=\"rank anonymous tooltip\"") && i == 1)
				{
					i = 0;
					int votes = Integer.valueOf(line.replaceAll("[^\\d]", ""));
					return votes;
				}
			}
			
			br.close();
			isr.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("Error while getting Hopzone server vote count.");
		}
		
		return -1;
	}
	
	private static int getVotes()
	{
		InputStreamReader isr = null;
		BufferedReader br = null;
		if(!hopzoneUrl.endsWith(".html"))
			hopzoneUrl+=".html";
		try
		{
			URLConnection con = new URL(hopzoneUrl).openConnection();
			con.addRequestProperty("User-L2Hopzone", "Mozilla/4.76");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("<li><span class=\"rank anonymous tooltip\""))
				{
					int votes = Integer.valueOf(line.replaceAll("[^\\d]", ""));
					return votes;
				}
			}
			
			br.close();
			isr.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("Error while getting server vote count.");
		}
		
		return -1;
	}
}