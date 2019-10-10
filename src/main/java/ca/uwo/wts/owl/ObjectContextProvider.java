package ca.uwo.wts.owl;

import java.util.Optional;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;

public class ObjectContextProvider
{
    // The server runtime and the read context must persist through the application runtime
    private static Optional<ServerRuntime> serverRuntime = Optional.empty();
    private static Optional<ObjectContext> readContext = Optional.empty();

    private ObjectContextProvider() {}

    /**
     * Gets the serverRuntime instance; constructs one if it doesn't exist
     */
    private static ServerRuntime getServerRuntime()
    {
        if (!serverRuntime.isPresent())
        {
            serverRuntime = Optional.of(ServerRuntime.builder().addConfig("cayenne-owldb.xml").build());
        }

        return serverRuntime.get();
    }

    /**
     * Gets an ObjectContext for reading that persists throughout the application runtime.
     * Writing in this context is strongly discouraged
     * (there is no way yet to enforce an ObjectContext as read only)
     */
    public static ObjectContext getReadContext()
    {
        if (!readContext.isPresent())
        {
            readContext = Optional.of(getServerRuntime().newContext());
        }

        return readContext.get();
    }
}
