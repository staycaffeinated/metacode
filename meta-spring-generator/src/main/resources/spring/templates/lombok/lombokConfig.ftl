config.stopBubbling = true

# Jacoco will usually exclude a class from
# code coverage if Jacoco detects a '@Generated'
# annotation. It doesn't have to be Lombok's
# @Generated annotation; a custom one also works.
# This is not a perfect solution; sometimes
# classes with @Generated will still be picked up
# by code coverage, and thus have to be manually
# excluded.
lombok.addLombokGeneratedAnnotation = true