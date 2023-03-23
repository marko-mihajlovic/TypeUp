#noinspection ShrinkerUnresolvedReference

-include proguard-rules.pro


-keep class com.typeup.util.SharedPref {*;}
-keep class com.typeup.search_apps.data.data_source.InstalledAppsDataSource {*;}
-keep class com.typeup.search_apps.data.data_source.test.FakeInstalledAppsDataSource {*;}
-keep class com.typeup.search_apps.data.data_source.test.FakeInstalledAppsDataSource2 {*;}
-keep class com.typeup.search_apps.data.repo.InstalledAppsRepo {*;}
-keep class com.typeup.search_apps.data.repo.InstalledAppsRepoImpl {*;}
-keep class com.typeup.search_apps.data.repo.InstalledAppsRepo$DefaultImpls {*;}
-keep class com.typeup.search_apps.data.model.AppsRepoState {*;}
-keep class com.typeup.search_apps.data.model.AppInfo {*;}

-keep class kotlin.LazyKt {*;}
-keep class kotlin.jvm.internal.Intrinsics {*;}
-keep class kotlin.io.CloseableKt {*;}
-keep class kotlin.text.StringsKt {*;}
-keep class kotlin.coroutines.Continuation {*;}
-keep class kotlin.coroutines.jvm.internal.DebugMetadata {*;}
-keep class kotlin.coroutines.jvm.internal.SuspendLambda {*;}
-keep class kotlin.coroutines.intrinsics.IntrinsicsKt {*;}
-keep class kotlin.coroutines.jvm.internal.Boxing {*;}
-keep class kotlin.ResultKt {*;}
-keep class kotlin.collections.CollectionsKt {*;}

-keep class kotlinx.coroutines.BuildersKt {*;}
-keep class kotlinx.coroutines.flow.FlowKt {*;}
-keep class kotlinx.coroutines.DelayKt {*;}
-keep class kotlinx.serialization.json.Json {*;}

-keep class androidx.tracing.Trace {*;}
-keep class com.google.common.util.concurrent.ListenableFuture {*;}
