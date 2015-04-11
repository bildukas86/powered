package Extensions.CCB.Manager.Tasks;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

public final class SpecialServices
{
	private static final Logger _log = Logger.getLogger(SpecialServices.class.getName());
	
	public static void openBrowserURL(String url)
	{
		try
		{
			if (Desktop.isDesktopSupported())
			{
				Desktop.getDesktop().browse(new URI(url));
			}
			else
			{
				Runtime runtime = Runtime.getRuntime();
				runtime.exec("/usr/bin/firefox -new-window " + url);
			}
		}
		catch (Exception e)
		{
			_log.warning("Failed to open url " + url + " " + e.getMessage());
		}
	}
}