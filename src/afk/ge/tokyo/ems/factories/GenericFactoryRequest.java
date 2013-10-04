package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a generic factory request of components and name/value pairs. Also
 * includes a collection of requests that correspond to any dependents that may
 * also need to be built during the construction process.
 *
 * @author Daniel
 */
public class GenericFactoryRequest implements FactoryRequest
{

    public static final String COMPONENT_PACKAGE = "afk.ge.tokyo.ems.components.";
    protected String name;
    protected Map<Class, Map<String, String>> components = new HashMap<Class, Map<String, String>>();
    protected Collection<GenericFactoryRequest> dependents = new ArrayList<GenericFactoryRequest>();

    public GenericFactoryRequest(String name)
    {
        this.name = name;
    }
    
    /**
     * Parses an AFK configuration file and generates a GenericFactoryRequest
     * from it.
     *
     * @param name the name of the configuration file to load from.
     * @return the generated GenericFactoryRequest from the file
     * @throws IOException if any error occurred loading or parsing the file
     */
    public static GenericFactoryRequest load(String name) throws IOException
    {
        GenericFactoryRequest request = new GenericFactoryRequest(name);
        BufferedReader in = new BufferedReader(new FileReader("config/" + name + ".afk"));
        try
        {
            String line;
            String className = null;
            HashMap<String, String> fields = new HashMap<String, String>();
            while ((line = in.readLine()) != null)
            {
                if (line.trim().isEmpty())
                {
                    continue; // ignore blank lines
                }
                if (line.matches("^\\s+.*$")) // [tab/space] name = value pair
                {
                    if (className == null)
                    {
                        throw new IOException("Unexpected field");
                    }
                    String[] nameValue = line.trim().split("\\s*=\\s*");
                    fields.put(nameValue[0], nameValue[1]);
                } else if (line.matches("^->\\s*.*$")) // -> dependent
                {
                    String dependentName = line.substring(2).trim();
                    try
                    {
                        request.dependents.add(load(dependentName));
                    } catch (IOException ex)
                    {
                        throw new IOException("Error loading dependent: " + dependentName, ex);
                    }
                } else // ^component class name
                {
                    try
                    {
                        className = line.trim();
                        request.components.put(Class.forName(COMPONENT_PACKAGE + className),
                                fields = new HashMap<String, String>());
                    } catch (ReflectiveOperationException ex)
                    {
                        throw new IOException(ex);
                    }
                }
            }
        } finally
        {
            in.close();
        }
        return request;
    }
}
