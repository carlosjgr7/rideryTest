tasks.register("createModuleStructure") {
    doLast {
        val moduleName = "main"

        val baseDir = "${projectDir}/src/main/java/com/carlosjgr7/riderytest/$moduleName"
        val dirs = listOf(
            "$baseDir/data/datasource/local",
            "$baseDir/data/datasource/network/request",
            "$baseDir/data/datasource/network/response",
            "$baseDir/data/datasource/network/di",
            "$baseDir/data/repository",
            "$baseDir/data/model",
            "$baseDir/di",
            "$baseDir/domain",
            "$baseDir/error",
            "$baseDir/presentation/ui",
            "$baseDir/presentation/viewmodels",
        )

        dirs.forEach { dir ->
            File(dir).mkdirs()
        }

        println("Estructura de carpetas creada para el módulo: $moduleName")
    }
}