package simulator.view;

/**
 * @author Daniel Lopez
 * @author Biao Chen
 */
public class Messages {// have all the messages when throwing a new Exception

	public static final String INVALID_DELTA_TIME_MESSAGE = "Invalid delta time value . It must be positive";
	public static final String INVALID_STEP_MESSAGE = "Invalid step value . It must be positive";
	public static final String GROUP_ID_NOT_PRESENT_MESSAGE = "There is not any group with the id (%s)";
	public static final String GROUP_ID_REPEATED_MESSAGE = "There is already a group with the id (%s)";
	public static final String BODY_ID_REPEATED_MESSAGE = "There is already a body in the group %s with the same id (%s)";
	public static final String INVALID_ARGUMENTS_NUMBER_MESSAGE = "Invalid number of arguments. %s should have %d arguments";

	public static final String INVALID_ID_MESSAGE = INVALID_DATA("id");
	public static final String INVALID_TYPE_TAG_MESSAGE = INVALID_DATA("type tag");
	public static final String INVALID_DESCRIPTION_MESSAGE = INVALID_DATA("description");
	public static final String INVALID_DATA_MESSAGE = INVALID_DATA("data");

	private static final String INVALID_DATA(String dataType) {
		return "Invalid " + dataType + ". %s is not a valid " + dataType;
	}

	public static final String INVALID_GRAVITATIONAL_CONSTANT_MESSAGE = INVALID_NEGATIVE_DATA("Gravitational Constant");
	public static final String INVALID_ACCELERATION_MESSAGE = INVALID_NEGATIVE_DATA("Acceleration");
	public static final String INVALID_MASS_MESSAGE = INVALID_NEGATIVE_DATA("Mass");

	private static String INVALID_NEGATIVE_DATA(String dataType) {
		return "Invalid " + dataType + ". " + dataType + " must be positive";
	}

	public static final String OBSERVER_ALREADY_OBSERVING = "The observer is already observing the physics simulator";
	public static final String OBSERVER_NOT_PRESENT = "The observer can't be removed as it wasn't observing the physics simulator";

	public static final String INVALID_GID_MESSAGE = INVALID_NULL_DATA("Group Id");
	public static final String INVALID_FORCE_LAW_MESSAGE = INVALID_NULL_DATA("Force Law");
	public static final String INVALID_POINT_MESSAGE = INVALID_NULL_DATA("Position for Fixed Point");
	public static final String INVALID_POSITION_MESSAGE = INVALID_NULL_DATA("Position");
	public static final String INVALID_VELOCITY_MESSAGE = INVALID_NULL_DATA("Velocity");
	public static final String INVALID_BODY_MESSAGE = INVALID_NULL_DATA("Body");

	private static String INVALID_NULL_DATA(String dataType) {
		return "Invalid " + dataType + ". " + dataType + " can not be null";
	}

	public static final String INVALID_INSTANCE_INFO_NULL = "Invalid value for createInstance: null";
	public static final String INVALID_INSTANCE_VALUE = "Invalid value for createInstance: ";

	public static final String KEY_MISSING_MESSAGE = "The key %s is missing";

	// Button icons and messages
	public final static String QUIT_MESSAGE = "Quit";
	public final static String QUIT_ICON = "resources/icons/exit.png";

	public final static String RUN_MESSAGE = "Run the simulator";
	public final static String RUN_ICON = "resources/icons/run.png";

	public final static String LOAD_MESSAGE = "Load an input file into the simulator";
	public final static String LOAD_ICON = "resources/icons/open.png";

	public final static String PHYSICS_MESSAGE = "Select force laws for groups";
	public final static String PHYSICS_ICON = "resources/icons/physics.png";

	public final static String VIEWER_MESSAGE = "Open viewer window";
	public final static String VIEWER_ICON = "resources/icons/viewer.png";

	public final static String STOP_MESSAGE = "Stop the simulator";
	public final static String STOP_ICON = "resources/icons/stop.png";
}
