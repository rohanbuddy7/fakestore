## Lumoslogic Task
Android app that uses the FakeStore API to fetch and display a list of products, allowing users to view product details

## Architecture
- MVVM + Repository pattern  
- Retrofit + OkHttp for API calls  
- Room for caching last successful response  
- StateFlow + NetworkResult for UI state (Loading/Success/Error)  
- Jetpack Compose + Navigation  


## Key Decisions & Trade-offs
- Implemented **offline support** (fallback to Room cache on failure), not full offline-first sync
- API success updates Room so cached data stays fresh
- Error handling


## Known Limitations
- Paging not implemented (loads full list)
- Detail cache works only after opening the product once
- No pull-to-refresh UI yet
