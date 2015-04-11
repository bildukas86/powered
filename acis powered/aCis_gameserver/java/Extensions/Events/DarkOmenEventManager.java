package Extensions.Events;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

import net.sf.l2j.gameserver.datatables.NpcTable;
import net.sf.l2j.gameserver.datatables.SpawnTable;
import net.sf.l2j.gameserver.model.L2Spawn;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.util.Rnd;

public class DarkOmenEventManager
{
	
	private static Logger _log = Logger.getLogger(DarkOmenEventManager.class.getName());
	private static Set<L2Spawn> _monsters = new CopyOnWriteArraySet<>();
	
	public static boolean darkOmenRunning = false;
	
	static int[] mobs =
	{
		21162,
		21253,
		21184,
		21205,
		21163,
		21254,
		21206,
		21185,
		21255,
		21207,
		21165,
		21186
	};
	
	public static DarkOmenEventManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	DarkOmenEventManager()
	{
		_log.info("DarkOmenEventManager: Event handler initialized.");
	}
	
	public static void despawnMnosters()
	{
		for (L2Spawn s : _monsters)
		{
			s.getLastSpawn().deleteMe();
			s.stopRespawn();
			SpawnTable.getInstance().deleteSpawn(s, false);
		}
		_log.info("Dark Omen Event End: Clearing the mess...");
		_monsters.clear();
		_log.info("Dark Omen Event End: " + _monsters.size() + " monsters deleted.");
	}
	
	public static void spawnAMonster(int x, int y, int z)
	{
		L2Spawn _monster = null;
		NpcTemplate monster = NpcTable.getInstance().getTemplate(mobs[Rnd.get(0, 11)]);
		
		try
		{
			_monster = new L2Spawn(monster);
			_monster.setLocx(x);
			_monster.setLocy(y);
			_monster.setLocz(z);
			_monster.setHeading(1);
			_monster.setRespawnDelay(15);
			_monster.init();
			_monster.getLastSpawn().decayMe();
			_monster.getLastSpawn().spawnMe(_monster.getLastSpawn().getX(), _monster.getLastSpawn().getY(), _monster.getLastSpawn().getZ());
			SpawnTable.getInstance().addNewSpawn(_monster, false);
			_monsters.add(_monster);
			_log.info("Dark Omen Event Start: Spawned " + _monsters.size() + " monsters.");
		}
		catch (Exception e)
		{
		}
	}
	
	private static void spawnANpc(int id, int x, int y, int z)
	{
		L2Spawn _monster = null;
		NpcTemplate monster = NpcTable.getInstance().getTemplate(id);
		
		try
		{
			_monster = new L2Spawn(monster);
			_monster.setLocx(x);
			_monster.setLocy(y);
			_monster.setLocz(z);
			_monster.setHeading(1);
			_monster.setRespawnDelay(15);
			_monster.init();
			_monster.getLastSpawn().decayMe();
			_monster.getLastSpawn().spawnMe(_monster.getLastSpawn().getX(), _monster.getLastSpawn().getY(), _monster.getLastSpawn().getZ());
			SpawnTable.getInstance().addNewSpawn(_monster, false);
			_monsters.add(_monster);
		}
		catch (Exception e)
		{
		}
	}
	
	private static void spawnABoss(int id, int x, int y, int z)
	{
		L2Spawn _monster = null;
		NpcTemplate monster = NpcTable.getInstance().getTemplate(id);
		
		try
		{
			_monster = new L2Spawn(monster);
			_monster.setLocx(x);
			_monster.setLocy(y);
			_monster.setLocz(z);
			_monster.setHeading(1);
			_monster.setRespawnDelay(2700);
			_monster.init();
			_monster.getLastSpawn().decayMe();
			_monster.getLastSpawn().spawnMe(_monster.getLastSpawn().getX(), _monster.getLastSpawn().getY(), _monster.getLastSpawn().getZ());
			SpawnTable.getInstance().addNewSpawn(_monster, false);
			_monsters.add(_monster);
		}
		catch (Exception e)
		{
		}
	}
	
	private static class SingletonHolder
	{
		protected static final DarkOmenEventManager _instance = new DarkOmenEventManager();
	}
	
	public static void spawnMonsters()
	{
		spawnABoss(25283, -113866, -173122, -6754);
		spawnABoss(25286, -120286, -177753, -6754);
		spawnANpc(7027, -82162, 150962, -3129);
		spawnANpc(7033, -8505, 14471, -4901);
		spawnANpc(7034, -8497, 14711, -4901);
		spawnAMonster(-18835, 14691, -4901);
		spawnAMonster(-18837, 14863, -4901);
		spawnAMonster(-18838, 15092, -4901);
		spawnAMonster(-18836, 15347, -4904);
		spawnAMonster(-18841, 15591, -4901);
		spawnAMonster(-18840, 15843, -4901);
		spawnAMonster(-19084, 15784, -4899);
		spawnAMonster(-19075, 15570, -4899);
		spawnAMonster(-19072, 15367, -4899);
		spawnAMonster(-19068, 15162, -4899);
		spawnAMonster(-19064, 14954, -4899);
		spawnAMonster(-19063, 14717, -4899);
		spawnAMonster(-19308, 14642, -4901);
		spawnAMonster(-19332, 14815, -4901);
		spawnAMonster(-19343, 15033, -4901);
		spawnAMonster(-19340, 15302, -4901);
		spawnAMonster(-19338, 15572, -4901);
		spawnAMonster(-19338, 15834, -4901);
		spawnAMonster(-18089, 18321, -4901);
		spawnAMonster(-18006, 18158, -4901);
		spawnAMonster(-18073, 17957, -4901);
		spawnAMonster(-17974, 17779, -4901);
		spawnAMonster(-18066, 17601, -4901);
		spawnAMonster(-17966, 17361, -4901);
		spawnAMonster(-18052, 17125, -4904);
		spawnAMonster(-17963, 16935, -4901);
		spawnAMonster(-18056, 16737, -4901);
		spawnAMonster(-17936, 16642, -4901);
		spawnAMonster(-17600, 16672, -4901);
		spawnAMonster(-17590, 16841, -4901);
		spawnAMonster(-17641, 16957, -4901);
		spawnAMonster(-17572, 17118, -4901);
		spawnAMonster(-17660, 17307, -4901);
		spawnAMonster(-17488, 17568, -4901);
		spawnAMonster(-17618, 17664, -4901);
		spawnAMonster(-17589, 17859, -4901);
		spawnAMonster(-17678, 18068, -4901);
		spawnAMonster(-17582, 18253, -4901);
		spawnAMonster(-14243, 17890, -4901);
		spawnAMonster(-14360, 17889, -4901);
		spawnAMonster(-14600, 17896, -4901);
		spawnAMonster(-14708, 17997, -4901);
		spawnAMonster(-14708, 18215, -4901);
		spawnAMonster(-14595, 18335, -4901);
		spawnAMonster(-14482, 18380, -4899);
		spawnAMonster(-14479, 18155, -4899);
		spawnAMonster(-14486, 17883, -4899);
		spawnAMonster(-14213, 18096, -4899);
		spawnAMonster(-14351, 18108, -4899);
		spawnAMonster(-14675, 18099, -4899);
		spawnAMonster(-13164, 18333, -4901);
		spawnAMonster(-13312, 18324, -4901);
		spawnAMonster(-13548, 18310, -4901);
		spawnAMonster(-13608, 18172, -4899);
		spawnAMonster(-13581, 18026, -4899);
		spawnAMonster(-13539, 17890, -4901);
		spawnAMonster(-13404, 17887, -4899);
		spawnAMonster(-13220, 17901, -4901);
		spawnAMonster(-13189, 18057, -4899);
		spawnAMonster(-13391, 18112, -4899);
		spawnAMonster(-13618, 18932, -4901);
		spawnAMonster(-13613, 19077, -4901);
		spawnAMonster(-13614, 19231, -4899);
		spawnAMonster(-13616, 19407, -4901);
		spawnAMonster(-13489, 19409, -4901);
		spawnAMonster(-13339, 19414, -4899);
		spawnAMonster(-13153, 19413, -4901);
		spawnAMonster(-13158, 19252, -4899);
		spawnAMonster(-13163, 19030, -4901);
		spawnAMonster(-13259, 19022, -4901);
		spawnAMonster(-13365, 19146, -4899);
		spawnAMonster(-13405, 19263, -4899);
		spawnAMonster(-13504, 20856, -4901);
		spawnAMonster(-13227, 20838, -4901);
		spawnAMonster(-12821, 20831, -4901);
		spawnAMonster(-12494, 20833, -4901);
		spawnAMonster(-12182, 20825, -4901);
		spawnAMonster(-11884, 20824, -4901);
		spawnAMonster(-12199, 20904, -4901);
		spawnAMonster(-12708, 20907, -4901);
		spawnAMonster(-13056, 20920, -4901);
		spawnAMonster(-13483, 20923, -4901);
		spawnAMonster(-13538, 20375, -4901);
		spawnAMonster(-13395, 20487, -4901);
		spawnAMonster(-13111, 20392, -4901);
		spawnAMonster(-13055, 20506, -4901);
		spawnAMonster(-12745, 20399, -4901);
		spawnAMonster(-12525, 20500, -4901);
		spawnAMonster(-12183, 20396, -4904);
		spawnAMonster(-12014, 20507, -4901);
		spawnAMonster(-11826, 20424, -4901);
		spawnAMonster(-11812, 20658, -4899);
		spawnAMonster(-12188, 20637, -4899);
		spawnAMonster(-12573, 20624, -4899);
		spawnAMonster(-12950, 20623, -4899);
		spawnAMonster(-13297, 20624, -4899);
		spawnAMonster(-13576, 20627, -4899);
		spawnAMonster(-10263, 20055, -4901);
		spawnAMonster(-10259, 19762, -4901);
		spawnAMonster(-10256, 19514, -4901);
		spawnAMonster(-10254, 19181, -4901);
		spawnAMonster(-10239, 18899, -4901);
		spawnAMonster(-10038, 18916, -4899);
		spawnAMonster(-10103, 19155, -4899);
		spawnAMonster(-9993, 19234, -4899);
		spawnAMonster(-10110, 19544, -4899);
		spawnAMonster(-10001, 19613, -4899);
		spawnAMonster(-9997, 19894, -4899);
		spawnAMonster(-10061, 20002, -4899);
		spawnAMonster(-9853, 20097, -4901);
		spawnAMonster(-9814, 19988, -4901);
		spawnAMonster(-9901, 19878, -4901);
		spawnAMonster(-9817, 19786, -4901);
		spawnAMonster(-9902, 19699, -4901);
		spawnAMonster(-9804, 19589, -4901);
		spawnAMonster(-9898, 19489, -4901);
		spawnAMonster(-9788, 19324, -4901);
		spawnAMonster(-9868, 19225, -4901);
		spawnAMonster(-9798, 19055, -4901);
		spawnAMonster(-9875, 18969, -4901);
		spawnAMonster(-9756, 18843, -4901);
		spawnAMonster(-8986, 19421, -4901);
		spawnAMonster(-9044, 19238, -4899);
		spawnAMonster(-8941, 19234, -4899);
		spawnAMonster(-8914, 19086, -4901);
		spawnAMonster(-9015, 19021, -4901);
		spawnAMonster(-8830, 18986, -4899);
		spawnAMonster(-8725, 19042, -4899);
		spawnAMonster(-8552, 18962, -4901);
		spawnAMonster(-8547, 19143, -4899);
		spawnAMonster(-8679, 19179, -4899);
		spawnAMonster(-8745, 19258, -4899);
		spawnAMonster(-8613, 19380, -4901);
		spawnAMonster(-8540, 19298, -4901);
		spawnAMonster(-8573, 19480, -4901);
		spawnAMonster(-8789, 18115, -4899);
		spawnAMonster(-8599, 18117, -4899);
		spawnAMonster(-8554, 17939, -4901);
		spawnAMonster(-8642, 17949, -4901);
		spawnAMonster(-8712, 17875, -4899);
		spawnAMonster(-8794, 17911, -4899);
		spawnAMonster(-8896, 17936, -4901);
		spawnAMonster(-8998, 17904, -4904);
		spawnAMonster(-9047, 18001, -4901);
		spawnAMonster(-8938, 18042, -4899);
		spawnAMonster(-8984, 18190, -4899);
		spawnAMonster(-8924, 18292, -4901);
		spawnAMonster(-9066, 18324, -4901);
		spawnAMonster(-12334, 15827, -4901);
		spawnAMonster(-12196, 15818, -4901);
		spawnAMonster(-12016, 15827, -4901);
		spawnAMonster(-11742, 15834, -4901);
		spawnAMonster(-11465, 15834, -4904);
		spawnAMonster(-11221, 15833, -4901);
		spawnAMonster(-10971, 15835, -4901);
		spawnAMonster(-10697, 15837, -4901);
		spawnAMonster(-10634, 15744, -4901);
		spawnAMonster(-10758, 15710, -4901);
		spawnAMonster(-10889, 15700, -4901);
		spawnAMonster(-11017, 15704, -4901);
		spawnAMonster(-11138, 15697, -4901);
		spawnAMonster(-11259, 15686, -4901);
		spawnAMonster(-11382, 15676, -4899);
		spawnAMonster(-11504, 15671, -4899);
		spawnAMonster(-11799, 15680, -4904);
		spawnAMonster(-12033, 15664, -4899);
		spawnAMonster(-12285, 15668, -4899);
		spawnAMonster(-12323, 15524, -4899);
		spawnAMonster(-12158, 15511, -4899);
		spawnAMonster(-11912, 15509, -4899);
		spawnAMonster(-11659, 15496, -4899);
		spawnAMonster(-11406, 15491, -4899);
		spawnAMonster(-11140, 15482, -4901);
		spawnAMonster(-10878, 15475, -4901);
		spawnAMonster(-10649, 15476, -4901);
		spawnAMonster(-10713, 15303, -4901);
		spawnAMonster(-10915, 15327, -4901);
		spawnAMonster(-11159, 15334, -4901);
		spawnAMonster(-11430, 15327, -4901);
		spawnAMonster(-11702, 15323, -4901);
		spawnAMonster(-11973, 15321, -4904);
		spawnAMonster(-12245, 15322, -4901);
		spawnAMonster(-12432, 15311, -4901);
		spawnAMonster(-13151, 16587, -4901);
		spawnAMonster(-13156, 16378, -4901);
		spawnAMonster(-13165, 16032, -4904);
		spawnAMonster(-13172, 15683, -4901);
		spawnAMonster(-13170, 15380, -4901);
		spawnAMonster(-13650, 15386, -4901);
		spawnAMonster(-13644, 15560, -4904);
		spawnAMonster(-13647, 15777, -4901);
		spawnAMonster(-13642, 16096, -4904);
		spawnAMonster(-13637, 16399, -4904);
		spawnAMonster(-13627, 16598, -4901);
		spawnAMonster(-13434, 16640, -4899);
		spawnAMonster(-13356, 16553, -4899);
		spawnAMonster(-13442, 16471, -4899);
		spawnAMonster(-13363, 16398, -4899);
		spawnAMonster(-13450, 16313, -4899);
		spawnAMonster(-13358, 16087, -4899);
		spawnAMonster(-13464, 15857, -4899);
		spawnAMonster(-13322, 15688, -4899);
		spawnAMonster(-13435, 15449, -4899);
		spawnAMonster(-13337, 15369, -4899);
		spawnAMonster(-13591, 13299, -4901);
		spawnAMonster(-13634, 13477, -4902);
		spawnAMonster(-13622, 13704, -4901);
		spawnAMonster(-13481, 13743, -4901);
		spawnAMonster(-13268, 13725, -4901);
		spawnAMonster(-13176, 13579, -4899);
		spawnAMonster(-13274, 13501, -4902);
		spawnAMonster(-13399, 13477, -4902);
		spawnAMonster(-14686, 13285, -4901);
		spawnAMonster(-14470, 13318, -4899);
		spawnAMonster(-14283, 13312, -4901);
		spawnAMonster(-14268, 13483, -4899);
		spawnAMonster(-14290, 13681, -4901);
		spawnAMonster(-14500, 13699, -4902);
		spawnAMonster(-14500, 13699, -4899);
		spawnAMonster(-14649, 13685, -4901);
		spawnAMonster(-14758, 13635, -4901);
		spawnAMonster(-14691, 13512, -4899);
		spawnAMonster(-14450, 13528, -4899);
		spawnAMonster(-17364, 14069, -4901);
		spawnAMonster(-17098, 14066, -4901);
		spawnAMonster(-16800, 14060, -4901);
		spawnAMonster(-16474, 14064, -4901);
		spawnAMonster(-16198, 14066, -4901);
		spawnAMonster(-16128, 13911, -4899);
		spawnAMonster(-16130, 13673, -4901);
		spawnAMonster(-16210, 13569, -4901);
		spawnAMonster(-16452, 13573, -4901);
		spawnAMonster(-16812, 13571, -4901);
		spawnAMonster(-17179, 13575, -4901);
		spawnAMonster(-17336, 13636, -4901);
		spawnAMonster(-17321, 13770, -4899);
		spawnAMonster(-17214, 13710, -4901);
		spawnAMonster(-17100, 13842, -4899);
		spawnAMonster(-16888, 13755, -4899);
		spawnAMonster(-16648, 13907, -4899);
		spawnAMonster(-16394, 13725, -4899);
		spawnAMonster(-16305, 13812, -4899);
		spawnAMonster(-16149, 13890, -4899);
		spawnAMonster(-9002, 16838, -4901);
		spawnAMonster(-8806, 16916, -4899);
		spawnAMonster(-8534, 16951, -4901);
		spawnAMonster(-8513, 16817, -4901);
		spawnAMonster(-8523, 16555, -4904);
		spawnAMonster(-8541, 16284, -4901);
		spawnAMonster(-8541, 16016, -4901);
		spawnAMonster(-8541, 15753, -4901);
		spawnAMonster(-8685, 15750, -4899);
		spawnAMonster(-8836, 15809, -4899);
		spawnAMonster(-9010, 15783, -4901);
		spawnAMonster(-9008, 15957, -4904);
		spawnAMonster(-8956, 16210, -4901);
		spawnAMonster(-8872, 16380, -4901);
		spawnAMonster(-8970, 16575, -4901);
		spawnAMonster(-8952, 16836, -4901);
		spawnAMonster(-8733, 16556, -4899);
		spawnAMonster(-8757, 16276, -4899);
		spawnAMonster(-11442, 13274, -4901);
		spawnAMonster(-11252, 13230, -4901);
		spawnAMonster(-11143, 13336, -4901);
		spawnAMonster(-10879, 13227, -4901);
		spawnAMonster(-10767, 13373, -4901);
		spawnAMonster(-10470, 13246, -4904);
		spawnAMonster(-10324, 13377, -4901);
		spawnAMonster(-10197, 13241, -4901);
		spawnAMonster(-10206, 13580, -4899);
		spawnAMonster(-10347, 13751, -4901);
		spawnAMonster(-10408, 13656, -4901);
		spawnAMonster(-10589, 13725, -4901);
		spawnAMonster(-10745, 13613, -4901);
		spawnAMonster(-10879, 13744, -4901);
		spawnAMonster(-11007, 13626, -4901);
		spawnAMonster(-11182, 13741, -4901);
		spawnAMonster(-11325, 13649, -4901);
		spawnAMonster(-11428, 13759, -4901);
		spawnAMonster(-11373, 13536, -4899);
		spawnAMonster(-10902, 13486, -4902);
		spawnAMonster(-10545, 13494, -4899);
		spawnAMonster(-8862, 21453, -4901);
		spawnAMonster(-9049, 21526, -4901);
		spawnAMonster(-9144, 21433, -4901);
		spawnAMonster(-9305, 21484, -4901);
		spawnAMonster(-9391, 21424, -4901);
		spawnAMonster(-9539, 21520, -4901);
		spawnAMonster(-9673, 21453, -4901);
		spawnAMonster(-9823, 21526, -4901);
		spawnAMonster(-9914, 21396, -4901);
		spawnAMonster(-10016, 21606, -4899);
		spawnAMonster(-9904, 21689, -4899);
		spawnAMonster(-10045, 21896, -4901);
		spawnAMonster(-9896, 21868, -4901);
		spawnAMonster(-9746, 21864, -4901);
		spawnAMonster(-9611, 21799, -4901);
		spawnAMonster(-9485, 21875, -4901);
		spawnAMonster(-9365, 21806, -4901);
		spawnAMonster(-9231, 21902, -4901);
		spawnAMonster(-9126, 21808, -4901);
		spawnAMonster(-8957, 21898, -4901);
		spawnAMonster(-8878, 21793, -4901);
		spawnAMonster(-8760, 21905, -4901);
		spawnAMonster(-8906, 21661, -4899);
		spawnAMonster(-9198, 21676, -4899);
		spawnAMonster(-9422, 21603, -4899);
		spawnAMonster(-9657, 21693, -4899);
		spawnAMonster(-9903, 21620, -4899);
		spawnAMonster(-11922, 21748, -4901);
		spawnAMonster(-12077, 21835, -4901);
		spawnAMonster(-12368, 21708, -4901);
		spawnAMonster(-12554, 21825, -4901);
		spawnAMonster(-12829, 21755, -4901);
		spawnAMonster(-13060, 21859, -4901);
		spawnAMonster(-13333, 21762, -4901);
		spawnAMonster(-13494, 21874, -4901);
		spawnAMonster(-13492, 22142, -4901);
		spawnAMonster(-13305, 22240, -4904);
		spawnAMonster(-13163, 22139, -4901);
		spawnAMonster(-12879, 22237, -4901);
		spawnAMonster(-12795, 22108, -4901);
		spawnAMonster(-12610, 22301, -4901);
		spawnAMonster(-12271, 22335, -4904);
		spawnAMonster(-12231, 22199, -4901);
		spawnAMonster(-11991, 22111, -4904);
		spawnAMonster(-11771, 22210, -4904);
		spawnAMonster(-11803, 22033, -4899);
		spawnAMonster(-12354, 21958, -4900);
		spawnAMonster(-12851, 21975, -4899);
		spawnAMonster(-13319, 21959, -4902);
		spawnAMonster(-13196, 23624, -4901);
		spawnAMonster(-13359, 23567, -4899);
		spawnAMonster(-13567, 23567, -4901);
		spawnAMonster(-13551, 23700, -4904);
		spawnAMonster(-13574, 23834, -4899);
		spawnAMonster(-13566, 23994, -4904);
		spawnAMonster(-13429, 24069, -4899);
		spawnAMonster(-13326, 24011, -4899);
		spawnAMonster(-13193, 23980, -4901);
		spawnAMonster(-13163, 23823, -4902);
		spawnAMonster(-13277, 23788, -4899);
		spawnAMonster(-13406, 23747, -4899);
		spawnAMonster(-13396, 23911, -4899);
		spawnAMonster(-10148, 23774, -4901);
		spawnAMonster(-10143, 23514, -4901);
		spawnAMonster(-10151, 23210, -4901);
		spawnAMonster(-10159, 22950, -4901);
		spawnAMonster(-10165, 22681, -4901);
		spawnAMonster(-10337, 22558, -4899);
		spawnAMonster(-10438, 22570, -4899);
		spawnAMonster(-10572, 22599, -4901);
		spawnAMonster(-10600, 22729, -4901);
		spawnAMonster(-10541, 22988, -4901);
		spawnAMonster(-10604, 23202, -4901);
		spawnAMonster(-10506, 23475, -4901);
		spawnAMonster(-10608, 23600, -4901);
		spawnAMonster(-10492, 23808, -4901);
		spawnAMonster(-10410, 23645, -4899);
		spawnAMonster(-10419, 23481, -4899);
		spawnAMonster(-10395, 23234, -4899);
		spawnAMonster(-10404, 22948, -4899);
		spawnAMonster(-10402, 22682, -4899);
		spawnAMonster(-10301, 22776, -4899);
		spawnAMonster(-14530, 15329, -4901);
		spawnAMonster(-14813, 15324, -4901);
		spawnAMonster(-15073, 15223, -4901);
		spawnAMonster(-15953, 15198, -4901);
		spawnAMonster(-15812, 15093, -4901);
		spawnAMonster(-15624, 15169, -4901);
		spawnAMonster(-15464, 15125, -4901);
		spawnAMonster(-15293, 15195, -4901);
		spawnAMonster(-15160, 15119, -4901);
		spawnAMonster(-14986, 15067, -4901);
		spawnAMonster(-15313, 14930, -4899);
		spawnAMonster(-15741, 14897, -4899);
		spawnAMonster(-15876, 14701, -4901);
		spawnAMonster(-15405, 14705, -4901);
		spawnAMonster(-15305, 14819, -4901);
		spawnAMonster(-15145, 14741, -4901);
		spawnAMonster(-14805, 14816, -4901);
		spawnAMonster(-14730, 14714, -4901);
		spawnAMonster(-14454, 14831, -4901);
		spawnAMonster(-14330, 14716, -4901);
		spawnAMonster(-14519, 14899, -4899);
		spawnAMonster(-15886, 14937, -4899);
		spawnAMonster(-18178, 14823, -4901);
		spawnAMonster(-18046, 14828, -4901);
		spawnAMonster(-17891, 14827, -4899);
		spawnAMonster(-17717, 14821, -4901);
		spawnAMonster(-17804, 14690, -4899);
		spawnAMonster(-17927, 14652, -4899);
		spawnAMonster(-18068, 14629, -4899);
		spawnAMonster(-18100, 14446, -4901);
		spawnAMonster(-17947, 14411, -4899);
		spawnAMonster(-17808, 14421, -4901);
		spawnAMonster(-17705, 14425, -4901);
		spawnAMonster(-17750, 14618, -4899);
		spawnAMonster(-17765, 14739, -4901);
		spawnAMonster(-17744, 14871, -4901);
		spawnAMonster(-17621, 14657, -4899);
		spawnAMonster(-16577, 16419, -4901);
		spawnAMonster(-16382, 16371, -4901);
		spawnAMonster(-16259, 16422, -4901);
		spawnAMonster(-16065, 16362, -4901);
		spawnAMonster(-15902, 16411, -4901);
		spawnAMonster(-15734, 16348, -4901);
		spawnAMonster(-15640, 16442, -4901);
		spawnAMonster(-15335, 16512, -4901);
		spawnAMonster(-15389, 16132, -4899);
		spawnAMonster(-15535, 15971, -4901);
		spawnAMonster(-15656, 16072, -4901);
		spawnAMonster(-15812, 15983, -4901);
		spawnAMonster(-15923, 16100, -4901);
		spawnAMonster(-16094, 15958, -4901);
		spawnAMonster(-16206, 16067, -4901);
		spawnAMonster(-16382, 15937, -4901);
		spawnAMonster(-16461, 16065, -4901);
		spawnAMonster(-16640, 15948, -4901);
		spawnAMonster(-16537, 16225, -4899);
		spawnAMonster(-16277, 16218, -4899);
		spawnAMonster(-16020, 16220, -4899);
		spawnAMonster(-15813, 16223, -4899);
		spawnAMonster(-15517, 16226, -4899);
		spawnAMonster(-15296, 16224, -4899);
		spawnAMonster(-14148, 18098, -4899);
		spawnAMonster(-14148, 18098, -4899);
		spawnAMonster(-10456, 17886, -4901);
		spawnAMonster(-10631, 17966, -4901);
		spawnAMonster(-10850, 17848, -4901);
		spawnAMonster(-10946, 17960, -4901);
		spawnAMonster(-11153, 17861, -4901);
		spawnAMonster(-11319, 17969, -4901);
		spawnAMonster(-11479, 17859, -4901);
		spawnAMonster(-11551, 17944, -4901);
		spawnAMonster(-11657, 17838, -4901);
		spawnAMonster(-11642, 18251, -4901);
		spawnAMonster(-11607, 18370, -4901);
		spawnAMonster(-11465, 18346, -4901);
		spawnAMonster(-11403, 18214, -4901);
		spawnAMonster(-11302, 18336, -4901);
		spawnAMonster(-11210, 18200, -4899);
		spawnAMonster(-11095, 18356, -4901);
		spawnAMonster(-11001, 18218, -4901);
		spawnAMonster(-10906, 18327, -4901);
		spawnAMonster(-10815, 18249, -4901);
		spawnAMonster(-10648, 18377, -4901);
		spawnAMonster(-10574, 18220, -4901);
		spawnAMonster(-10422, 18366, -4901);
		spawnAMonster(-10465, 18172, -4899);
		spawnAMonster(-10752, 18132, -4899);
		spawnAMonster(-11028, 18126, -4899);
		spawnAMonster(-11296, 18113, -4899);
		spawnAMonster(-11560, 18114, -4899);
		spawnAMonster(-8563, 14763, -4901);
		spawnAMonster(-8716, 14725, -4899);
		spawnAMonster(-8827, 14767, -4899);
		spawnAMonster(-8931, 14747, -4901);
		spawnAMonster(-9041, 14736, -4901);
		spawnAMonster(-9009, 14451, -4901);
		spawnAMonster(-8906, 14423, -4901);
		spawnAMonster(-8802, 14397, -4899);
		spawnAMonster(-8668, 14373, -4901);
		spawnAMonster(-8535, 14436, -4901);
		spawnAMonster(-8990, 14387, -4901);
		spawnAMonster(-8890, 14452, -4901);
		spawnAMonster(-8911, 14552, -4899);
		spawnAMonster(-8951, 14662, -4899);
		spawnAMonster(-9004, 14825, -4901);
		spawnAMonster(-8809, 14805, -4899);
		spawnAMonster(-13386, 15834, -4899);
		spawnAMonster(-13534, 15746, -4901);
		spawnAMonster(-13536, 13596, -4901);
		spawnAMonster(-15484, 14892, -4899);
		spawnAMonster(-15468, 15057, -4901);
		spawnAMonster(-14920, 14952, -4899);
		spawnAMonster(-14419, 15071, -4901);
		spawnAMonster(-14604, 15134, -4901);
		spawnAMonster(-15755, 16249, -4899);
		spawnAMonster(-9027, 13275, -4901);
		spawnAMonster(-8818, 13327, -4899);
		spawnAMonster(-8710, 13331, -4899);
		spawnAMonster(-8628, 13292, -4901);
		spawnAMonster(-8571, 13478, -4899);
		spawnAMonster(-8797, 13622, -4899);
		spawnAMonster(-8931, 13704, -4901);
		spawnAMonster(-8929, 13593, -4901);
	}
}