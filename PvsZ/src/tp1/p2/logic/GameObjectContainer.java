package tp1.p2.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.view.Messages;

public class GameObjectContainer {

	private List<GameObject> gameObjects;

	public GameObjectContainer() {
		gameObjects = new ArrayList<>();

	}

	public void add(GameObject object) {
		object.onEnter();
		this.gameObjects.add(object);

	}

	public String positionToString(int x, int y) {//
		StringBuilder buffer = new StringBuilder();
		boolean sunPainted = false;
		boolean sunAboutToPaint = false;

		for (GameObject g : gameObjects) {
			if (g.isAlive() && g.getCol() == y && g.getRow() == x) {
				String objectText = g.toString();
				sunAboutToPaint = objectText.indexOf(Messages.SUN_SYMBOL) >= 0;
				if (sunAboutToPaint) {
					if (!sunPainted) {
						buffer.append(objectText);
						sunPainted = true;
					}
				} else {
					buffer.append(objectText);
				}
			}
		}

		return buffer.toString();

	}

	public void update() {

		for (int i = 0; i < this.gameObjects.size(); i++) {

			this.gameObjects.get(i).update();
		}
		this.removeDead();
		// this.look();
	}

	public boolean isPositionEmpty(int col, int row) {
		return this.getObject(col, row) == null;
	}

	public GameObject getObject(int col, int row) {
		for (GameObject c : this.gameObjects) {
			if (c.isInPosition(col, row) && c.fillPosition()) {
				return c;
			}
		}
		return null;
	}

	public void reset() {
		this.gameObjects.clear();
	}

	public boolean removeDead() {
		List<GameObject> auxiliar = new ArrayList<>();
		boolean DeadObject = false;

		for (int i = 0; i < this.gameObjects.size(); i++) {
			if (this.gameObjects.get(i).isAlive()) {

				auxiliar.add(this.gameObjects.get(i));
			} else {
				this.gameObjects.get(i).onExit();
				DeadObject = true;
			}
		}
		this.gameObjects = auxiliar;
		return DeadObject;
	}

	public boolean catchObject(int y, int x) {

		boolean cogido = false;
		for (int i = 0; i < this.gameObjects.size(); i++) {
			if (this.gameObjects.get(i).isInPosition(y, x)) {
				if (this.gameObjects.get(i).catchObject())
					cogido = true;
			}

		}

		return cogido;
	}
}
