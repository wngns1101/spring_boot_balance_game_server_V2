plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.4.0")
}

rootProject.name = "balance_game_V2"

include("api")
//include("backoffice")
include("domain")
