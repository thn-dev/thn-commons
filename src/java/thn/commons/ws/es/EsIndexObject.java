package thn.commons.ws.es;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;

public class EsIndexObject
{
    private final HashMap<String, String> index;

    public EsIndexObject()
    {
        index = new HashMap<>();
    }

    public void setId(final String id)
    {
        checkNotNull(id, "Missing id");
        index.put("_id", id);
    }

    public void setIndex(final String indexName)
    {
        checkNotNull(indexName, "Missing index name");
        index.put("_index", indexName);
    }

    public void setType(final String type)
    {
        checkNotNull(type, "Missing type");
        index.put("_type", type);
    }
}
