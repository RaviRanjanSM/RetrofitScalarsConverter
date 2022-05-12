# RetrofitScalarsConverter
Retrofit scalars converters are also used to web scrapping in Android. You can use web scrapping to extract information from different articles
 we’ll be implementing Web Scraping in our Android Application. We will be scraping  to get all the Image listed on the home page. We’ll be using the Retrofit library to read web pages.
 we’ll be using ScalarConverter to parse the website passed in the Retrofit request. We’ll fetch all Images  in the RecyclerView.
 
 There can be instances when you just need image as the response body from the network call.
In such cases, instead of GsonConverters, we need to use Scalars Converter

In order to use Scalar Converters, you need to add the following dependency along with Retrofit  dependencies in the <h1>build.gradle</h1>
