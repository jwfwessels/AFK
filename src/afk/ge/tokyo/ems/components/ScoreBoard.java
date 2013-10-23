package afk.ge.tokyo.ems.components;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Keeps score of the various robots in a single match.
 * 
 * @author daniel
 */
public class ScoreBoard
{
    public Map<UUID, Integer> scores = new HashMap<UUID, Integer>();
}
