package BrAlchemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import org.brad.randoms.MysteriousOldMan;
import org.brad.randoms.SandwichLady;
import org.brad.utils.ItemManager;
import org.brad.utils.Methods;
import org.brad.utils.Teleports;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Random;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.LoopTask;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.api.methods.BotMouse;
import org.rev317.api.methods.Magic;
import org.rev317.api.methods.Players;
import org.rev317.api.methods.Magic.StandardMagic377;
import org.rev317.api.wrappers.hud.Item;

@ScriptManifest(author = "Bradsta", category = Category.MAGIC, description = "Alches items!", name = "BrAlchemy", servers = { "PkHonor" }, version = 1.000)
public class BrAlchemy extends Script implements LoopTask, Paintable {

	// Stores script tasks, only using for randoms.
	private final ArrayList<Strategy> tasks = new ArrayList<>();

	@Override
	public boolean onExecute() {
		tasks.add(new SandwichLady());
		tasks.add(new MysteriousOldMan());
		provide(tasks);
		return true;
	}

	// Script states
	private enum State {
		ACTIVE_RANDOM, CAST_SPELL;
	}

	/**
	 * Returns script state based on conditions
	 * 
	 * @return Script state
	 */
	private State getState() {
		if (tasks.get(0).activate() || tasks.get(1).activate()) {
			return State.ACTIVE_RANDOM;
		}
		return State.CAST_SPELL;
	}

	/**
	 * Attempts to cast the spell
	 */
	private void castSpell() {
		if (Teleports.openMagicTab()) {
			final Item item = ItemManager.getItem(557);
			if (item != null) {
				Magic.castSpell(StandardMagic377.CHARGE_WATER_ORB, item);
				if (Players.getLocal().getAnimation() != -1)
					sleep(Random.between(700, 850));
			}
		}
	}

	@Override
	public int loop() {
		switch (getState()) {
		case ACTIVE_RANDOM:
			sleep(Random.between(1500, 2000));
			break;
		case CAST_SPELL:
			castSpell();
			break;
		default:
			break;
		}
		return 200;
	}

	@Override
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Methods.plusMouse(g, Color.RED, new Point(BotMouse.getMouseX(),
				BotMouse.getMouseY()));
	}

}
