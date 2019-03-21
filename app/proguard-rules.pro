-keepnames class androidx.appcompat.app.AppCompatViewInflater
-dontnote sun.misc.Unsafe
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}
-dontwarn okio.DeflaterSink
-dontwarn okio.Okio

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn com.google.crypto.tink.**

-dontwarn com.google.protobuf.**
-dontwarn com.nineoldandroids.animation.**
-dontwarn com.nineoldandroids.view.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontnote com.github.ajalt.reprint.module.marshmallow.**
-dontnote com.google.android.material.**
-dontnote kotlin.coroutines.jvm.internal.**
-dontnote kotlin.internal.PlatformImplementationsKt
-dontnote kotlin.jvm.internal.Reflection
-dontnote okhttp3.internal.platform.**
-dontnote com.google.gson.internal.UnsafeAllocator
-dontwarn retrofit2.Platform$Java8
-keep class ru.geogram.data.model.db.projects.** { *; }
-keep class ru.geogram.data.model.network.** { *; }
