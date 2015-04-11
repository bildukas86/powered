package Extensions.CCB.Manager.Tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.L2DatabaseFactory;

public class AnnouncementsUpdateManager
{
	static Logger _log = Logger.getLogger(AnnouncementsUpdateManager.class.getName());
	private static StringBuilder _announcementsList = new StringBuilder();
	
	public static void loadSection()
	{
		_log.log(Level.INFO, "[CommunityBoard]: Loading announcements section.");
		try
		{
			Connection con = L2DatabaseFactory.getInstance().getConnection();
			Throwable localThrowable3 = null;
			try
			{
				PreparedStatement st = con.prepareStatement("SELECT announce_id,announce_title,announce_date FROM bbs_announcements ORDER BY announce_id DESC LIMIT 5;");
				ResultSet rset = st.executeQuery();
				
				int id = 0;
				String title = "";
				String date = "";
				while (rset.next())
				{
					id = rset.getInt("announce_id");
					title = rset.getString("announce_title");
					date = rset.getString("announce_date");
					
					_announcementsList.append("<table width=610 height=40><tr>");
					_announcementsList.append("<td width=50 align=center>" + id + "</td>");
					_announcementsList.append("<td width=400 align=left><a action=\"bypass _bbsannouncements;read " + id + "\">" + title + "</a></td>");
					_announcementsList.append("<td width=100 align=center>" + date + "</td>");
					_announcementsList.append("</tr></table><img src=\"l2ui.squaregray\" width=\"610\" height=\"1\">");
				}
				rset.close();
				st.close();
			}
			catch (Throwable localThrowable1)
			{
				localThrowable3 = localThrowable1;
				throw localThrowable1;
			}
			finally
			{
				if (con != null)
				{
					if (localThrowable3 != null)
					{
						try
						{
							con.close();
						}
						catch (Throwable localThrowable2)
						{
							localThrowable3.addSuppressed(localThrowable2);
						}
					}
					else
					{
						con.close();
					}
				}
			}
		}
		catch (SQLException e)
		{
			_log.log(Level.WARNING, "Failed to load announcements list " + e.getMessage(), e);
		}
	}
	
	public static void updateList()
	{
		_log.log(Level.INFO, "[CommunityBoard]: Announcements section has been updated.");
		
		_announcementsList.delete(0, _announcementsList.length());
		try
		{
			Connection con = L2DatabaseFactory.getInstance().getConnection();
			Throwable localThrowable3 = null;
			try
			{
				PreparedStatement st = con.prepareStatement("SELECT announce_id,announce_title,announce_date FROM bbs_announcements ORDER BY announce_id DESC LIMIT 5;");
				ResultSet rset = st.executeQuery();
				
				int id = 0;
				String title = "";
				String date = "";
				while (rset.next())
				{
					id = rset.getInt("announce_id");
					title = rset.getString("announce_title");
					date = rset.getString("announce_date");
					
					_announcementsList.append("<table width=610 height=40><tr>");
					_announcementsList.append("<td width=50 align=center>" + id + "</td>");
					_announcementsList.append("<td width=400 align=left><a action=\"bypass _bbsannouncements;read " + id + "\">" + title + "</a></td>");
					_announcementsList.append("<td width=100 align=center>" + date + "</td>");
					_announcementsList.append("</tr></table><img src=\"l2ui.squaregray\" width=\"610\" height=\"1\">");
				}
				rset.close();
				st.close();
			}
			catch (Throwable localThrowable1)
			{
				localThrowable3 = localThrowable1;
				throw localThrowable1;
			}
			finally
			{
				if (con != null)
				{
					if (localThrowable3 != null)
					{
						try
						{
							con.close();
						}
						catch (Throwable localThrowable2)
						{
							localThrowable3.addSuppressed(localThrowable2);
						}
					}
					else
					{
						con.close();
					}
				}
			}
		}
		catch (SQLException e)
		{
			_log.log(Level.WARNING, "Failed to update announcements list " + e.getMessage(), e);
		}
	}
	
	public static String showList()
	{
		return _announcementsList.toString();
	}
}