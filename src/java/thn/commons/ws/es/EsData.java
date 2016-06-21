package thn.commons.ws.es;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

public class EsData
{
    private final HashMap<String, Object> data;

    public EsData()
    {
        data = new HashMap<>();
    }

    public void add(final String key, final Object value)
    {
        if (!data.containsKey(key))
        {
            if (value instanceof String)
            {
                data.put(key, ((String)value).trim());
            }
            else
            {
                data.put(key, value);
            }
        }
        else
        {
            if ((data.get(key) instanceof String) && (value instanceof String))
            {
                if (!StringUtils.contains((String) data.get(key), ((String) value).trim()))
                {
                    data.put(key, String.format("%s, %s", data.get(key), ((String) value).trim()));
                }
            }
        }
    }

    public Object get(final String key)
    {
        return data.get(key);
    }

    public boolean containsKey(final String key)
    {
        return data.containsKey(key);
    }

    public HashMap<String, Object> getData()
    {
        return data;
    }
}
