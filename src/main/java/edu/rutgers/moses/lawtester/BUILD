package(default_visibility = ["//visibility:public"])

java_library(
    name = "test_agent",
    srcs = ["TestAgent.java"],
    deps = [
        "//third_party:moses",
    ],
)

java_library(
    name = "test_agent_builder",
    srcs = ["TestAgentBuilder.java"],
    deps = ["//src/main/java/edu/rutgers/moses/lawtester:test_agent"],
)

java_library(
    name = "receives_delegate",
    srcs = ["ReceivesDelegate.java"],
    deps = [],
)
