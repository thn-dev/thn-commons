package thn.commons.ws.es;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import thn.commons.ws.JerseyUtils;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;

public class EsJersey
{
    private final static Logger log = Logger.getLogger(EsJersey.class);

    private final static String FORWARD_SLASH = "/";
    private final static String LINE_BREAK = "\n";

    private final static String ES_HOST = "http://localhost:9200";
    private final static String ES_BULK = "_bulk";
    private final static String ES_COUNT = "_count";
    private final static String ES_SEARCH = "_search";
    private final static String ES_STATUS = "_status";
    private final static String ES_OPTIMIZE = "_optimize";

    private final String esUrl;
    private final Client client;
    private final Gson gson;

    public EsJersey(final String esUrl)
    {
        this.esUrl = esUrl;
        this.gson = new Gson();
        this.client = JerseyUtils.create();
    }

    public void close()
    {
        JerseyUtils.destroy(client);
    }

    public String getSearch()
    {
        final String url = addAction(esUrl, ES_SEARCH);
        return JerseyUtils.get(client, url);
    }

    public String getCount()
    {
        final String url = addAction(esUrl, ES_COUNT);
        return JerseyUtils.get(client, url);
    }

    public String getIndexCount(final String index)
    {
        final String url = addAction(addIndex(esUrl, index), ES_COUNT);
        return JerseyUtils.get(client, url);
    }

    public String getIndexTypeCount(final String index, final String type)
    {
        final String url = addAction(addType(addIndex(esUrl, index), type), ES_COUNT);
        return JerseyUtils.get(client, url);
    }

    public String getDocById(final String index, final String type, final String id)
    {
        final String url = addId(addType(addIndex(esUrl, index), type), id);
        return JerseyUtils.get(client, url);
    }

    public String bulkIndex(final String index, final String type, final String content)
    {
        final String url = addAction(addType(addIndex(esUrl, index), type), ES_BULK);
        String results = "Unable to index";

        try
        {
            results = JerseyUtils.post(client, url, content);
        }
        catch (final UniformInterfaceException e)
        {
            log.info(results, e);
        }
        return results;
    }

    public String bulkIndex(final String index, final String type, final List<EsData> docDatum)
    {
        final StringBuilder bulkData = new StringBuilder();
        for (final EsData docData : docDatum)
        {
            final EsIndexObject doc = new EsIndexObject();
            if (docData.containsKey("id"))
            {
                doc.setId((String) docData.get("id"));
            }
            else
            {
                doc.setId(UUID.randomUUID().toString());
            }
            bulkData.append(gson.toJson(doc)).append(LINE_BREAK).append(gson.toJson(docData.getData())).append(LINE_BREAK);
        }
        return bulkIndex(index, type, bulkData.toString());
    }

    public String bulkIndexStringList(final String index, final String type, final List<String> datum)
    {
        final StringBuilder bulkData = new StringBuilder();
        for (final String data : datum)
        {
            final EsIndexObject doc = new EsIndexObject();
            doc.setId(UUID.randomUUID().toString());
            bulkData.append(gson.toJson(doc)).append(LINE_BREAK).append(data).append(LINE_BREAK);
        }
        return bulkIndex(index, type, bulkData.toString());
    }

    // ------------------------------------------------------------------------
    // index methods
    // ------------------------------------------------------------------------
    public String indexWithId(final String index, final String type, final String id, final String content)
    {
        final String url = addId(addType(addIndex(esUrl, index), type), id);
        return JerseyUtils.post(client, url, content);
    }

    public String index(final String index, final String type, final String content)
    {
        final String url = addType(addIndex(esUrl, index), type);
        return JerseyUtils.post(client, url, content);
    }



    // ------------------------------------------------------------------------
    // delete methods
    // ------------------------------------------------------------------------
    public String deleteIndex(final String index)
    {
        final String url = addIndex(esUrl, index);
        return JerseyUtils.delete(client, url);
    }

    public String deleteType(final String index, final String type)
    {
        final String url = addType(addIndex(esUrl, index), type);
        return JerseyUtils.delete(client, url);
    }

    public String deleteId(final String index, final String type, final String id)
    {
        final String url = addId(addType(addIndex(esUrl, index), type), id);
        return JerseyUtils.delete(client, url);
    }

    // ------------------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------------------
    private String addAction(final String url, final String action)
    {
        checkNotNull(url, "Missing URL");
        checkNotNull(action, "Missing action");
        return appendToUrl(url, action);
    }

    private String addIndex(final String url, final String index)
    {
        checkNotNull(url, "Missing URL");
        checkNotNull(index, "Missing index");
        return appendToUrl(url, index);
    }

    private String addType(final String url, final String type)
    {
        checkNotNull(url, "Missing URL");
        checkNotNull(type, "Missing type");
        return appendToUrl(url, type);
    }

    private String addId(final String url, final String id)
    {
        checkNotNull(url, "Missing URL");
        checkNotNull(id, "Missing id");
        return appendToUrl(url, id);
    }

    private String appendToUrl(final String url, final String value)
    {
        checkNotNull(url, "Missing URL");
        checkNotNull(value, "Missing value");
        return String.format("%s/%s", url, value);
    }
}
