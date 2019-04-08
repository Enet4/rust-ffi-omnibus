include ${COMMON_TEST_RULES}

# The value of `d` changes between the time the target is evaluated
# and the time it is run. To work around this, *target-specific*
# variables are used.

# Compile the example. All dependencies must be explicitly listed.
${d}/src/main.class: LIB_DIR := ${LIB_DIR_${d}}
${d}/src/main.class: LIB_NAME := ${LIB_NAME_${d}}
${d}/src/main.class: ${d}/src/main.java ${LIB_${d}}
	javac -cp ${CLASSPATH} ${CLASS_DIR}/main.java

# Save the example output into a file by running it
${TEST_DIR_${d}}/java-test: LIB_DIR := ${LIB_DIR_${d}}
${TEST_DIR_${d}}/java-test: CLASS_DIR := ${d}/src
${TEST_DIR_${d}}/java-test: ${d}/src/main.class
	LD_LIBRARY_PATH=${LIB_DIR} java -cp ${CLASSPATH}:${CLASS_DIR} main > $@

# Compare the example output with the expected output. This does not
# produce any files, so it is marked `PHONY`.
.PHONY: java-test_${d}
java-test_${d}: EXPECTED := ${d}/expected-output
java-test_${d}: ${TEST_DIR_${d}}/java-test
	diff -q ${EXPECTED} $<

# Add this example's test target to the global list for this language.
java: java-test_${d}
