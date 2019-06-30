# Website Searcher

### Usage

`$ java -jar ./build/libs/wework-1.0-SNAPSHOT.jar`

Results are written to the `results.txt` file.

#### Changing the default Search Term

The default search term is "foo." You can change the search term by passing it in as a first argument.
Changing the search term: `$ java -jar ./build/libs/wework-1.0-SNAPSHOT.jar "wework 2019"`

If I had more time to work on this I would spend time making the following changes:

- More way to configure URL Lists
- Retry Logic
- End to End Integration Test