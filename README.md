
![螢幕擷取畫面 2024-03-20 150312](https://github.com/Quuanna/SearchImage/assets/36694083/8a909a99-5d7b-4685-88e4-23201032a20d)

# Architecture
   - MVVM Architecture (Model - View - ViewModel)
   - Repository Pattern
# Jetpack
   - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
   - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
   - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata): observable data holder class and lifecycle-aware, only update app Component observers that are in an active lifecycle state.
# Network
  - Retrofit、OKhttp :　Construct the REST APIs And network data
# Unit Test 
  - local unit tests
  - JVM testing
  - Instrumented testing
  - coroutine test

## 實作測試
  ### viewModel 
  - 測試商業邏輯
  ### liveData local unit tests
  - 使用 JUnit 測試規則 InstantTaskExecutorRule 同步執行每個任務的不同背景執行程式
  - 新增 LiveDataTestUtil.kt Class 使用 getOrAwaitValues 等待執行對 liveData.postValue、setValue，測試最終提供給View的資料驗證
  - 新增 single event，測試一次事件的邏輯 
  ### Repository
  - Make a Fake Data Source
    - **模擬回傳的資料** : 新增 FakeDataSource 實作interface DataSource，測試 RemoteDataSource、LocalDataSourc 
  - Write a Test Using Dependency Injection
    - Repository Constructor Dependency Injection 實作 interface DataSource
       - Task: Set up a Fake Repository
         - 使用FakeDataSource替代原Constructor Dependency Injection，測試資料來源 Remote、Local 是否正確
       - Task: Use the Fake Repository inside a ViewModel
         - FakeImagesRepositoryImpl 實作 interface Repository，仿造真實的實作，ViewModel Constructor Dependency Injection FakeRepository


