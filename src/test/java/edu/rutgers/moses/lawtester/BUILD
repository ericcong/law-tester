java_test(
    name = "LawTesterTest",
    srcs = ["LawTesterTest.java"],
    resources = [
        "//src/test/resources:dummy_law",
        "//src/test/resources:monitor_law",
        "//src/test/resources:monitor_lawtest",
        "//src/test/resources:pingpong_law",
        "//src/test/resources:pingpong_lawtest",
        "//src/test/resources:trivial_law",
        "//src/test/resources:trivial_lawtest",
    ],
    deps = [
        "//src/main/java/edu/rutgers/moses/lawtester:law_test_result",
        "//src/main/java/edu/rutgers/moses/lawtester:law_tester",
        "//third_party:moses",
    ],
)

java_test(
    name = "CoffeeScriptTest",
    srcs = ["CoffeeScriptTest.java"],
    deps = [
        "//src/main/java/edu/rutgers/moses/lawtester:coffee_script",
    ],
)
