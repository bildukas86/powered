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

public class VoteRewardTopzone
{
	// Configurations.
	private static String topzoneUrl = Config.TOPZONE_SERVER_LINK;
	private static String page1Url = Config.TOPZONE_FIRST_PAGE_LINK;
	private static int voteRewardVotesDifference = Config.TOPZONE_VOTES_DIFFERENCE;
	private static int firstPageRankNeeded = Config.TOPZONE_FIRST_PAGE_RANK_NEEDED;
	private static int checkTime = 60 * 1000 * Config.TOPZONE_REWARD_CHECK_TIME;
	
	// Don't-touch variables.
	private static int lastVotes = 0;
	private static HashMap<String, Integer> playerIps = new HashMap<>();
	
	public static void updateConfigurations()
	{
		topzoneUrl = Config.TOPZONE_SERVER_LINK;
		page1Url = Config.TOPZONE_FIRST_PAGE_LINK;
		voteRewardVotesDifference = Config.TOPZONE_VOTES_DIFFERENCE;
		firstPageRankNeeded = Config.TOPZONE_FIRST_PAGE_RANK_NEEDED;
		checkTime = 60 * 1000 * Config.TOPZONE_REWARD_CHECK_TIME;
	}
	
	public static void getInstance()
	{
		System.out.println("Topzone - Vote reward system initialized.");
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				if (Config.ALLOW_TOPZONE_VOTE_REWARD)
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
		
		if (firstPageVotes == -1 || currentVotes == -1)
		{
			if (firstPageVotes == -1)
			{
				System.out.println("There was a problem on getting Topzone votes from server with rank " + firstPageRankNeeded + ".");
			}
			if (currentVotes == -1)
			{
				System.out.println("There was a problem on getting Topzone server votes.");
			}
			
			return;
		}
		
		if (lastVotes == 0)
		{
			lastVotes = currentVotes;
			Announcements.announceToAll("Topzone: Current vote count is " + currentVotes + ".");
			Announcements.announceToAll("Topzone: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for reward.");
			if (Config.ALLOW_TOPZONE_GAME_SERVER_REPORT)
			{
				System.out.println("Server votes on topzone: " + currentVotes);
				System.out.println("Votes needed for reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
			}
			if (firstPageVotes - lastVotes <= 0)
			{
				Announcements.announceToAll("Topzone: We are in the top " + firstPageRankNeeded + " of topzone, so the reward will be big.");
				if (Config.ALLOW_TOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server is on the top " + firstPageRankNeeded + " of topzone.");
				}
			}
			else
			{
				Announcements.announceToAll("Topzone: We need " + (firstPageVotes - lastVotes) + " vote(s) to get to the top " + firstPageRankNeeded + " of topzone for big reward.");
				if (Config.ALLOW_TOPZONE_GAME_SERVER_REPORT)
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
				if (Config.ALLOW_TOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on topzone: " + currentVotes);
					System.out.println("Server is on the top " + firstPageRankNeeded + " of topzone.");
					System.out.println("Votes needed for next reward: " + ((currentVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.announceToAll("Topzone: Everyone has been rewarded with big reward.");
				Announcements.announceToAll("Topzone: Current vote count is " + currentVotes + ".");
				for (L2PcInstance p : pls)
				{
					boolean canReward = false;
					String pIp = p.getClient().getConnection().getInetAddress().getHostAddress();
					if (playerIps.containsKey(pIp))
					{
						int count = playerIps.get(pIp);
						if (count < Config.TOPZONE_DUALBOXES_ALLOWED)
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
						for (int i = 0; i < Config.TOPZONE_BIG_REWARD.length; i++)
						{
							p.addItem("Vote reward.", Config.TOPZONE_BIG_REWARD[i][0], Config.TOPZONE_BIG_REWARD[i][1], p, true);
						}
					}
					else
					{
						p.sendMessage("Already " + Config.TOPZONE_DUALBOXES_ALLOWED + " character(s) of your ip have been rewarded, so this character won't be rewarded.");
					}
				}
				playerIps.clear();
			}
			else
			{
				if (Config.ALLOW_TOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on topzone: " + currentVotes);
					System.out.println("Server votes needed for top " + firstPageRankNeeded + ": " + (firstPageVotes - lastVotes));
					System.out.println("Votes needed for next reward: " + ((currentVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.announceToAll("Topzone: Everyone has been rewarded with small reward.");
				Announcements.announceToAll("Topzone: Current vote count is " + currentVotes + ".");
				Announcements.announceToAll("Topzone: We need " + (firstPageVotes - currentVotes) + " vote(s) to get to the top " + firstPageRankNeeded + " of topzone for big reward.");
				for (L2PcInstance p : pls)
				{
					boolean canReward = false;
					String pIp = p.getClient().getConnection().getInetAddress().getHostAddress();
					if (playerIps.containsKey(pIp))
					{
						int count = playerIps.get(pIp);
						if (count < Config.TOPZONE_DUALBOXES_ALLOWED)
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
						for (int i = 0; i < Config.TOPZONE_SMALL_REWARD.length; i++)
						{
							p.addItem("Vote reward.", Config.TOPZONE_SMALL_REWARD[i][0], Config.TOPZONE_SMALL_REWARD[i][1], p, true);
						}
					}
					else
					{
						p.sendMessage("Already " + Config.TOPZONE_DUALBOXES_ALLOWED + " character(s) of your ip have been rewarded, so this character won't be rewarded.");
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
				if (Config.ALLOW_TOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on topzone: " + currentVotes);
					System.out.println("Server is on the top " + firstPageRankNeeded + " of topzone.");
					System.out.println("Votes needed for next reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.announceToAll("Topzone: Current vote count is " + currentVotes + ".");
				Announcements.announceToAll("Topzone: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for big reward.");
			}
			else
			{
				if (Config.ALLOW_TOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on topzone: " + currentVotes);
					System.out.println("Server votes needed for top " + firstPageRankNeeded + ": " + (firstPageVotes - lastVotes));
					System.out.println("Votes needed for next reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.announceToAll("Topzone: Current vote count is " + currentVotes + ".");
				Announcements.announceToAll("Topzone: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for small reward.");
				Announcements.announceToAll("Topzone: We need " + (firstPageVotes - currentVotes) + " vote(s) to get to the top " + firstPageRankNeeded + " of topzone for big reward.");
			}
		}
	}
	
	private static int getFirstPageRankVotes()
	{
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try
		{
			URLConnection con = new URL(page1Url).openConnection();
			con.addRequestProperty("User-Agent", "L2TopZone");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("<div class=\"slr\">" + firstPageRankNeeded + "<div class=\"votes\">Votes:<br><span>"))
				{
					int votes = Integer.valueOf(line.split("<div class=\"slr\">" + firstPageRankNeeded + "<div class=\"votes\">Votes:<br><span>")[1].replace("</span></div></div>", ""));
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
		
		try
		{
			URLConnection con = new URL(topzoneUrl).openConnection();
			con.addRequestProperty("User-Agent", "L2TopZone");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			boolean got = false;
			
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("<div class=\"rank\"><div class=\"votes2\">Votes:<br>") && !got)
				{
					got = true;
					int votes = Integer.valueOf(line.split("<div class=\"rank\"><div class=\"votes2\">Votes:<br>")[1].replace("</div></div>", ""));
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