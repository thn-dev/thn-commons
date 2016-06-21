package thn.commons.ws;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class JerseyUtils
{
    public static Client create()
    {
        return Client.create();
    }

    public static void destroy(final Client client)
    {
        if (null != client)
        {
            client.destroy();
        }
    }

    public static int getStatusCode(final ClientResponse response)
    {
        return response.getStatus();
    }

    public static WebResource createWebResource(final Client client, final String url)
    {
        checkNotNull(client, "Client cannot be null");
        checkNotNull(url, "Missing URL");
        return client.resource(url);
    }

    public static ClientResponse getWithClientResponse(final Client client, final String url)
    {
        return getWithClientResponse(client, url, null);
    }

    public static ClientResponse getWithClientResponse(final Client client, final String url, final MultivaluedMap<String, String> queryParams) throws UniformInterfaceException, ClientHandlerException
    {
        final WebResource webResource = createWebResource(client, url);

        final ClientResponse response;

        if (null != queryParams)
        {
            response = webResource.queryParams(queryParams).get(ClientResponse.class);
        }
        else
        {
            response = webResource.get(ClientResponse.class);
        }
        return response;
    }

    public static String get(final Client client, final String url) throws UniformInterfaceException, ClientHandlerException
    {
        return get(client, url, null);
    }

    public static String get(final Client client, final String url, final MultivaluedMap<String, String> queryParams) throws UniformInterfaceException, ClientHandlerException
    {
        final WebResource webResource = createWebResource(client, url);

        String results = null;
        if (null != queryParams)
        {
            results = webResource.queryParams(queryParams).get(String.class);
        }
        else
        {
            results = webResource.get(String.class);
        }

        return results;
    }

    public static MultivaluedMap<String, String> createQueryParameterMap(final String fieldName, final String fieldValue)
    {
        final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add(fieldName, fieldValue);
        return queryParams;
    }

    public static MultivaluedMap<String, String> addQueryParam(final MultivaluedMap<String, String> queryParams, final String fieldName, final String fieldValue)
    {
        queryParams.add(fieldName, fieldValue);
        return queryParams;
    }

    public static String createSolrQueryParameter(final String fieldName, final String fieldValue)
    {
        return String.format("%s: \"%s\"", fieldName, fieldValue);
    }

    public static String createQueryParameter(final String fieldName, final String fieldValue)
    {
        return String.format("%s=%s", fieldName, fieldValue);
    }

    public static String post(final Client client, final String url, final String content) throws UniformInterfaceException, ClientHandlerException
    {
        checkNotNull(content, "missing POST's content");
        final WebResource webResource = createWebResource(client, url);
        return webResource.post(String.class, content);
    }

    public static ClientResponse postWithClientResponse(final Client client, final String url, final String content) throws UniformInterfaceException, ClientHandlerException
    {
        checkNotNull(content, "missing POST's content");
        final WebResource webResource = createWebResource(client, url);
        return webResource.post(ClientResponse.class, content);
    }

    public static String delete(final Client client, final String url)  throws UniformInterfaceException, ClientHandlerException
    {
        final WebResource webResource = createWebResource(client, url);
        return webResource.delete(String.class);
    }
}
