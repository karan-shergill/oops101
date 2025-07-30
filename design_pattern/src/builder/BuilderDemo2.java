package builder;

class URL1 {
    /*
    The URL1 class represents the final product that is built using the Builder pattern.
    It is immutable, meaning once an instance is created, its state cannot be changed.
    */
    private final String protocol;
    private final String hostName;
    private final String pathParam;
    private final String queryParam;

    /*
    Private constructor to ensure that URL1 objects can only be created via the URLBuilder1 class.
    This enforces the use of the Builder pattern.
    */
    private URL1(URLBuilder1 urlBuilder) {
        this.protocol = urlBuilder.protocol;
        this.hostName = urlBuilder.hostName;
        this.pathParam = urlBuilder.pathParam;
        this.queryParam = urlBuilder.queryParam;
    }

    /*
    Method to construct the URL string based on the parameters set in the URL1 object.
    */
    public String getURL() {
        StringBuilder url = new StringBuilder(this.protocol + "://" + this.hostName);

        if (this.pathParam != null && !this.pathParam.isEmpty()) {
            url.append("/").append(this.pathParam);
        }

        if (this.queryParam != null && !this.queryParam.isEmpty()) {
            url.append("?").append(this.queryParam);
        }

        return url.toString();
    }

    /*
    URLBuilder1 is a static inner class of URL1 and provides the methods to build a URL1 object.
    It follows the Builder pattern by allowing incremental construction of the URL1 object.
    */
    public static class URLBuilder1 {
        private final String protocol; // Mandatory field
        private final String hostName; // Mandatory field
        private String pathParam; // Optional field
        private String queryParam; // Optional field

        /*
        Constructor that requires mandatory fields (protocol and hostName) to be set.
        */
        public URLBuilder1(String protocol, String hostName) {
            this.protocol = protocol;
            this.hostName = hostName;
        }

        /*
        Method to set the optional pathParam field.
        Returns the builder instance for method chaining.
        */
        public URLBuilder1 setPathParam(String pathParam) {
            this.pathParam = pathParam;
            return this; // Return the builder for fluent interface
        }

        /*
        Method to set the optional queryParam field.
        Returns the builder instance for method chaining.
        */
        public URLBuilder1 setQueryParam(String queryParam) {
            this.queryParam = queryParam;
            return this; // Return the builder for fluent interface
        }

        /*
        The build() method creates a new URL1 instance using the current state of the builder.
        */
        public URL1 build() {
            return new URL1(this);
        }
    }
}

// Director class to manage the construction process
class URLDirector {
    private URL1.URLBuilder1 builder;

    /*
    Constructor that takes a URLBuilder1 instance.
    The director will use this builder to construct the URL1 object.
    */
    public URLDirector(URL1.URLBuilder1 builder) {
        this.builder = builder;
    }

    /*
    Method to construct a URL1 object with both path and query parameters.
    Encapsulates the construction logic in a convenient method.
    */
    public URL1 constructURLWithPathAndQuery(String pathParam, String queryParam) {
        return builder.setPathParam(pathParam)
                .setQueryParam(queryParam)
                .build();
    }

    /*
    Method to construct a basic URL1 object without optional parameters.
    */
    public URL1 constructBasicURL() {
        return builder.build();
    }
}

public class BuilderDemo2 {
    public static void main(String[] args) {
        // Create a URLBuilder1 instance with mandatory fields
        URL1.URLBuilder1 builder = new URL1.URLBuilder1("https", "amazon.com");

        // Create a URLDirector instance with the builder
        URLDirector director = new URLDirector(builder);

        // Using the director to build URLs with specific configurations

        // Build a basic URL with mandatory fields only
        URL1 url1 = director.constructBasicURL();
        System.out.println(url1.getURL()); // Output: https://amazon.com

        // Build a URL with both path and query parameters
        URL1 url2 = director.constructURLWithPathAndQuery("shirt", "BA123123K");
        System.out.println(url2.getURL()); // Output: https://amazon.com/shirt?BA123123K
    }
}