package(default_visibility = ["//visibility:public"])

java_library(
    name = "test_agent",
    srcs = [
        "ReceivesDelegate.java",
        "SendDelegate.java",
        "TestAgent.java",
        "TestAgentReceiver.java",
    ],
    deps = [
        "//src/main/java/edu/rutgers/moses/lawtester:message_entry",
        "//third_party:moses",
        "@gson//jar",
    ],
)

java_library(
    name = "test_agent_builder",
    srcs = ["TestAgentBuilder.java"],
    deps = [
        "//src/main/java/edu/rutgers/moses/lawtester:test_agent",
    ],
)

java_library(
    name = "message_entry",
    srcs = ["MessageEntry.java"],
    deps = [],
)
