// UML Diagram: https://tinyurl.com/2a99lvaa

package builder;

class URL {
    /*
    The fields in this class are marked as final to ensure that they cannot be changed after the URL object is
    constructed. This makes the URL object immutable, which is a common feature in the Builder pattern.
    */
    private final String protocol;
    private final String hostName;
    private final String pathParam;
    private final String queryParam;

    // Private constructor to ensure URL objects are only created through the Builder
    private URL(URLBuilder urlBuilder) {
        this.protocol = urlBuilder.protocol;
        this.hostName = urlBuilder.hostName;
        this.pathParam = urlBuilder.pathParam;
        this.queryParam = urlBuilder.queryParam;
    }

    // Method to build the URL string based on the object's fields
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

    // Static inner class that provides the Builder pattern implementation
    public static class URLBuilder {
        /*
        The URLBuilder class uses the Builder pattern to allow flexible and incremental construction of a URL.
        The protocol and hostName fields are final, ensuring they must be provided when the builder is created.
        The pathParam and queryParam fields are non-final, allowing them to be set optionally.
        */
        private final String protocol;
        private final String hostName;
        private String pathParam;
        private String queryParam;

        // Constructor that requires mandatory fields to be set
        public URLBuilder(String protocol, String hostName) {
            this.protocol = protocol;
            this.hostName = hostName;
        }

        // Method to set the optional pathParam field
        public URLBuilder setPathParam(String pathParam) {
            this.pathParam = pathParam;
            return this; // Return the builder for method chaining
        }

        // Method to set the optional queryParam field
        public URLBuilder setQueryParam(String queryParam) {
            this.queryParam = queryParam;
            return this; // Return the builder for method chaining
        }

        /*
        The build() method is the final step in the Builder pattern. It creates and returns a new URL object
        using the current state of the URLBuilder. This method ensures that the URL object is fully constructed
        before it is returned.
        */
        public URL build() {
            return new URL(this);
        }
    }
}

public class BuilderDemo1 {
    public static void main(String[] args) {
        // Creating a URL with only mandatory fields
        URL url1 = new URL.URLBuilder("https", "amazon.com").build();
        System.out.println(url1.getURL()); // Output: https://amazon.com

        // Creating a URL with an optional path parameter
        URL url2 = new URL.URLBuilder("https", "amazon.com")
                .setPathParam("shirt")
                .build();
        System.out.println(url2.getURL()); // Output: https://amazon.com/shirt

        // Creating a URL with both optional path and query parameters
        URL url3 = new URL.URLBuilder("https", "amazon.com")
                .setPathParam("shirt")
                .setQueryParam("BA123123K")
                .build();
        System.out.println(url3.getURL()); // Output: https://amazon.com/shirt?BA123123K

        // Creating a URL with only an optional query parameter
        URL url4 = new URL.URLBuilder("https", "amazon.com")
                .setQueryParam("BA123123K")
                .build();
        System.out.println(url4.getURL()); // Output: https://amazon.com?BA123123K
    }
}

/*
OUTPUT:

https://amazon.com
https://amazon.com/shirt
https://amazon.com/shirt?BA123123K
https://amazon.com?BA123123K
*/