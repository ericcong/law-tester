package(default_visibility = ["//visibility:public"])

java_library(
    name = "law_test_result",
    srcs = ["LawTestResult.java"],
)

java_library(
    name = "coffee_script",
    srcs = ["CoffeeScript.java"],
    resources = [
        "//src/main/resources:coffee_script",
    ],
)

java_library(
    name = "law_tester",
    srcs = ["LawTester.java"],
    resources = [
        "//src/main/resources:coffee_script",
    ],
    deps = [
        "//src/main/java/edu/rutgers/moses/lawtester:law_test_result",
        "//third_party:moses",
    ],
)
