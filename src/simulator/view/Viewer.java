package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;

@SuppressWarnings("serial")
class Viewer extends SimulationViewer {

	private static final int _WIDTH = 500;
	private static final int _HEIGHT = 500;
	private static final int _SHIFT_AMOUNT = 10;

	private static final int _CIRCLE_RADIOUS = 6;

	private static final Color _FORCE_VECTOR_COLOR = Color.RED;
	private static final Color _VELOCITY_VECTOR_COLOR = Color.GREEN;
	private static final int _VECTOR_LENGTH = 25;
	private static final int _ARROW_HEIGHT = 4;
	private static final int _ARROW_WIDHT = 6;

	private static final int _HELP_X_COORDENATE_ORIGIN = 10;
	private static final int _HELP_Y_COORDENATE_ORIGIN = 15;
	private static final int _HELP_Y_COORDENATE_SEPARATION = 18;

	// (_centerX,_centerY) is used as the origin when drawing
	// the bodies
	private int _centerX;
	private int _centerY;

	// values used to shift the actual origin (the middle of
	// the window), when calculating (_centerX,_centerY)
	private int _originX = 0;
	private int _originY = 0;

	// the scale factor, used to reduce the bodies coordinates
	// to the size of the component
	private double _scale = 1.0;

	// indicates if the help message should be shown
	private boolean _showHelp = true;

	// indicates if the position/velocity vectors should be shown
	private boolean _showVectors = true;

	// the list bodies and groups
	private List<Body> _bodies;
	private List<BodiesGroup> _groups;

	// a color generator, and a map that assigns colors to groups
	private ColorsGenerator _colorGen;
	private Map<String, Color> _gColor;

	// the index and Id of the selected group, -1 and null means all groups
	private int _selectedGroupIdx = -1;
	private String _selectedGroup = null;

	Viewer() {
		initGUI();
	}

	private void shiftOrigin(int xDirection, int yDirection) {
		this._originX += _SHIFT_AMOUNT * xDirection;
		this._originY += _SHIFT_AMOUNT * yDirection;
	}

	private void resetOrigin() {
		this._originX = 0;
		this._originY = 0;
	}

	private void toggleHelp() {
		this._showHelp = !this._showHelp;
	}

	private void toggleVectors() {
		this._showVectors = !this._showVectors;
	}

	private void nextGroup() {
		this._selectedGroupIdx = (this._selectedGroupIdx + 2) % (this._groups.size() + 1) - 1;
		if (this._selectedGroupIdx == -1) {
			this._selectedGroup = null;
		} else {
			this._selectedGroup = this._groups.get(this._selectedGroupIdx).getId();
		}
	}

	private void initGUI() {

		// add a border
		setBorder(BorderFactory.createLineBorder(Color.black, 2));

		// initialize the color generator, and the map, that we use
		// assign colors to groups
		_colorGen = new ColorsGenerator();
		_gColor = new HashMap<>();

		// initialize the lists of bodies and groups
		_bodies = new ArrayList<>();
		_groups = new ArrayList<>();

		// The preferred and minimum size of the components
		setMinimumSize(new Dimension(_WIDTH, _HEIGHT));
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));

		// add a key listener to handle the user actions
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'j':
					shiftOrigin(-1, 0);
					repaint();
					break;
				case 'i':
					shiftOrigin(0, -1);
					repaint();
					break;
				case 'l':
					shiftOrigin(1, 0);
					repaint();
					break;
				case 'm':
					shiftOrigin(0, 1);
					repaint();
					break;
				case 'k':
					resetOrigin();
					repaint();
					break;
				case 'h':
					toggleHelp();
					repaint();
					break;
				case 'v':
					toggleVectors();
					repaint();
					break;
				case 'g':
					nextGroup();
					repaint();
					break;
				default:
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					repaint();
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					repaint();
					break;
				case '=':
					autoScale();
					repaint();
					break;

				default:
				}
			}
		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// a better graphics object
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// calculate the center
		_centerX = getWidth() / 2 - _originX;
		_centerY = getHeight() / 2 - _originY;

		gr.setColor(Color.RED);
		gr.drawLine(_centerX - 5, _centerY, _centerX + 5, _centerY);
		gr.drawLine(_centerX, _centerY - 5, _centerX, _centerY + 5);

		// draw bodies
		drawBodies(gr);

		// show help if needed
		if (_showHelp) {
			showHelp(gr);
		}
	}

	private void showHelp(Graphics2D g) {
		int y = _HELP_Y_COORDENATE_ORIGIN;
		g.setColor(Color.RED);
		g.drawString("h: toggle help, v: toggle vectors, +: zoom-in, -: zoom-out, =: fit", _HELP_X_COORDENATE_ORIGIN,
				y);
		y += _HELP_Y_COORDENATE_SEPARATION;
		g.drawString("g: show next group", _HELP_X_COORDENATE_ORIGIN, y);
		y += _HELP_Y_COORDENATE_SEPARATION;
		g.drawString("l: move right, j: move left, i: move up, m: move down: k: reset", _HELP_X_COORDENATE_ORIGIN, y);
		y += _HELP_Y_COORDENATE_SEPARATION;
		g.drawString("Scaling ratio: " + _scale, _HELP_X_COORDENATE_ORIGIN, y);
		y += _HELP_Y_COORDENATE_SEPARATION;
		g.setColor(Color.BLUE);
		g.drawString("Selected Group: " + (_selectedGroup != null ? _selectedGroup : "all"), _HELP_X_COORDENATE_ORIGIN,
				y);
	}

	private void drawBodies(Graphics2D g) {

		for (Body body : this._bodies) {
			if (isVisible(body)) {
				drawBody(body, g);
			}
		}
	}

	private void drawBody(Body body, Graphics2D g) {
		Vector2D bodyPositionScaled = new Vector2D(body.getPosition().getX(),-body.getPosition().getY()).scale(1.0/ _scale).plus(new Vector2D(_centerX, _centerY));
		
		if (this._showVectors) {
			drawVector(bodyPositionScaled, body.getForce(), this._FORCE_VECTOR_COLOR, g);
			drawVector(bodyPositionScaled, body.getVelocity(), this._VELOCITY_VECTOR_COLOR, g);
		}
		g.setColor(_gColor.get(body.getgId()));
		g.fillOval((int) bodyPositionScaled.getX() - _CIRCLE_RADIOUS, (int) bodyPositionScaled.getY() - _CIRCLE_RADIOUS,
				_CIRCLE_RADIOUS * 2, _CIRCLE_RADIOUS * 2);
		g.setColor(Color.BLACK);
		Rectangle2D bounds = g.getFont().getStringBounds(body.getId(), g.getFontRenderContext());
		g.drawString(body.getId(), (int) (bodyPositionScaled.getX() - bounds.getWidth() / 2),
				(int) (bodyPositionScaled.getY() - bounds.getHeight() / 2 - 4));
	}

	private void drawVector(Vector2D originPosition, Vector2D vector, Color vectorColor, Graphics2D g) {
		if (vector.magnitude() != 0) {
			Vector2D vectorDirection = new Vector2D(vector.direction().getX(),-vector.direction().getY());
			vectorDirection = vectorDirection.scale(_VECTOR_LENGTH).plus(originPosition);
			drawLineWithArrow(g, (int) originPosition.getX(), (int) originPosition.getY(), (int) vectorDirection.getX(),
					(int) vectorDirection.getY(), _ARROW_WIDHT, _ARROW_HEIGHT, vectorColor, vectorColor);
		}
	}

	private boolean isVisible(Body b) {
		return this._selectedGroup == null || this._selectedGroup.equals(b.getgId());
	}

	// calculates a value for scale such that all visible bodies fit in the window
	private void autoScale() {

		double max = 1.0;

		for (Body b : _bodies) {
			Vector2D p = b.getPosition();
			max = Math.max(max, Math.abs(p.getX()));
			max = Math.max(max, Math.abs(p.getY()));
		}

		double size = Math.max(1.0, Math.min(getWidth(), getHeight()));

		_scale = max > size ? 4.0 * max / size : 1.0;
	}

	@Override
	public void addGroup(BodiesGroup g) {
		this._groups.add(g);
		for (Body body : g) {
			addBody(body);
		}
		_gColor.put(g.getId(), _colorGen.nextColor()); // assign color to group
		autoScale();
		update();
	}

	@Override
	public void addBody(Body b) {
		this._bodies.add(b);
		autoScale();
		update();
	}

	@Override
	public void reset() {
		this._groups.clear();
		this._bodies.clear();
		this._gColor.clear();
		_colorGen.reset(); // reset the color generator
		_selectedGroupIdx = -1;
		_selectedGroup = null;
		update();
	}

	@Override
	void update() {
		repaint();
	}

	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(//
			Graphics g, //
			int x1, int y1, //
			int x2, int y2, //
			int w, int h, //
			Color lineColor, Color arrowColor) {

		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}

}
