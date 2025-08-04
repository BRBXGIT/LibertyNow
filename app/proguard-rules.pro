# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep inherited services.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# R8 full mode strips generic signatures from return types if not kept.
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# Keep model data classes
-keep class com.example.network.anime_screen.models.** { *; }
-keep class com.example.network.search_screen.models.** { *; }
-keep class com.example.network.common.models.** { *; }
-keep class com.example.network.auth.models.** { *; }
-keep class com.example.network.home_screen.models.** { *; }
-keep class com.example.local.db.lists_db.** { *; }
-keep class com.example.local.db.watched_eps_db.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

# Save methods parameters(@Query, @Path, etc.)
-keepattributes *Annotation*

# Clases with gson annotations
-keep class com.google.gson.** { *; }

# Сохраняем generic информацию TypeToken
-keepattributes Signature
-keep class com.google.gson.reflect.TypeToken
-keep class com.google.gson.reflect.TypeToken$* { *; }

# Это поможет сохранить параметризацию generic классов
-keep class * extends com.google.gson.reflect.TypeToken