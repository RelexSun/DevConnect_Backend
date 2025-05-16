package com.kshrd.devconnect_springboot.utils;

import com.kshrd.devconnect_springboot.model.templeJsonb.TestCase;

import java.util.List;
import java.util.stream.Collectors;

public class CodeGenerator {

    public static String generate(String lang, String header, String body, String fnName, List<TestCase> testCases) {
        // First, let's clean the body to ensure it doesn't contain a duplicate method definition
        body = cleanBody(body, header, fnName);

        switch (lang.toLowerCase()) {
            case "java":
                return generateJava(header, body, fnName, testCases);
            case "python":
                return generatePython(body, fnName, testCases);
            case "c":
                return generateC(header, body, fnName, testCases);
            case "cpp":
                return generateCpp(header, body, fnName, testCases);
            case "javascript":
                return generateJavaScript(header, body, fnName, testCases);
            case "csharp":
                return generateCSharp(header, body, fnName, testCases);
            case "php":
                return generatePHP(header, body, fnName, testCases);
            default:
                throw new IllegalArgumentException("Unsupported language: " + lang);
        }
    }

    /**
     * Ensures the body doesn't contain a duplicate method definition
     */
    private static String cleanBody(String body, String header, String fnName) {
        // If the body contains the function name and parenthesis, it might be a duplicate method definition
        if (body != null && body.contains(fnName + "(")) {
            // Look for the first opening brace after the function name
            int fnPos = body.indexOf(fnName + "(");
            int openBracePos = body.indexOf("{", fnPos);

            if (openBracePos != -1) {
                // Find the matching closing brace
                int closeBracePos = findMatchingCloseBrace(body, openBracePos);

                if (closeBracePos != -1 && closeBracePos > openBracePos) {
                    // Extract just the implementation inside the braces
                    return body.substring(openBracePos + 1, closeBracePos).trim();
                } else {
                    // If we can't find a proper closing brace, take everything after the opening brace
                    return body.substring(openBracePos + 1).trim();
                }
            }
        }
        return body;
    }

    private static int findMatchingCloseBrace(String text, int openBracePos) {
        int depth = 1;
        for (int i = openBracePos + 1; i < text.length(); i++) {
            if (text.charAt(i) == '{') {
                depth++;
            } else if (text.charAt(i) == '}') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1; // No matching close brace found
    }

    private static String generateJava(String header, String body, String fnName, List<TestCase> tests) {
        StringBuilder sb = new StringBuilder();
        sb.append("public class Solution {\n");

        // Add the method signature from header and open brace
        sb.append("    ").append(header).append(" {\n");

        // Add the method body (we've already cleaned it in the generate method)
        sb.append("        ").append(body).append("\n");
        sb.append("    }\n\n");

        sb.append("    public static void main(String[] args) {\n");
        for (TestCase t : tests) {
            String argsList = t.getInput().stream()
                    .map(CodeGenerator::formatJavaLiteral)
                    .collect(Collectors.joining(", "));
            sb.append("        System.out.println(").append(fnName).append("(").append(argsList).append("));\n");
        }
        sb.append("    }\n}");
        return sb.toString();
    }

    private static String generatePython(String body, String fnName, List<TestCase> tests) {
        StringBuilder sb = new StringBuilder();
        sb.append(body).append("\n"); // full Python function (with def ...)
        for (TestCase t : tests) {
            String argsList = t.getInput().stream()
                    .map(CodeGenerator::formatPythonLiteral)
                    .collect(Collectors.joining(", "));
            sb.append("print(").append(fnName).append("(").append(argsList).append("))\n");
        }
        return sb.toString();
    }

    private static String generateC(String header, String body, String fnName, List<TestCase> tests) {
        StringBuilder sb = new StringBuilder();
        sb.append("#include <stdio.h>\n");
        sb.append("#include <stdlib.h>\n");
        sb.append("#include <string.h>\n");
        sb.append("#include <math.h>\n");
        sb.append("#include <stdbool.h>\n");
        sb.append("\n");
        sb.append(header).append(" {\n    ").append(body).append("\n}\n");
        sb.append("int main() {\n");
        for (TestCase t : tests) {
            String argsList = t.getInput().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            sb.append("    printf(\"%s\\n\", ").append(fnName).append("(").append(argsList).append("));\n");
        }
        sb.append("    return 0;\n}");
        return sb.toString();
    }

    private static String generateCpp(String header, String body, String fnName, List<TestCase> tests) {
        StringBuilder sb = new StringBuilder();
        sb.append("#include <iostream>\nusing namespace std;\n");
        sb.append(header).append(" {\n    ").append(body).append("\n}\n");
        sb.append("int main() {\n");
        for (TestCase t : tests) {
            String argsList = t.getInput().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            sb.append("    cout << ").append(fnName).append("(").append(argsList).append(") << endl;\n");
        }
        sb.append("    return 0;\n}");
        return sb.toString();
    }

    private static String generateJavaScript(String header, String body, String fnName, List<TestCase> tests) {
        StringBuilder sb = new StringBuilder();

        // Extract function parameters from header
        String params = "";
        if (header != null && !header.isEmpty()) {
            int paramStart = header.indexOf('(');
            int paramEnd = header.lastIndexOf(')');
            if (paramStart != -1 && paramEnd != -1 && paramEnd > paramStart) {
                params = header.substring(paramStart + 1, paramEnd).trim();
            }
        }

        // Define the function
        sb.append("function ").append(fnName).append("(").append(params).append(") {\n");
        sb.append("    ").append(body).append("\n");
        sb.append("}\n\n");

        // Add test cases
        for (TestCase t : tests) {
            String argsList = t.getInput().stream()
                    .map(CodeGenerator::formatJavaScriptLiteral)
                    .collect(Collectors.joining(", "));
            sb.append("console.log(").append(fnName).append("(").append(argsList).append("));\n");
        }

        return sb.toString();
    }

    private static String generateCSharp(String header, String body, String fnName, List<TestCase> tests) {
        StringBuilder sb = new StringBuilder();

        sb.append("using System;\n\n");
        sb.append("class Program\n{\n");

        // Add the method
        sb.append("    ").append(header).append("\n    {\n");
        sb.append("        ").append(body).append("\n");
        sb.append("    }\n\n");

        // Add main method with test cases
        sb.append("    static void Main(string[] args)\n    {\n");

        for (TestCase t : tests) {
            String argsList = t.getInput().stream()
                    .map(CodeGenerator::formatCSharpLiteral)
                    .collect(Collectors.joining(", "));
            sb.append("        Console.WriteLine(").append(fnName).append("(").append(argsList).append("));\n");
        }

        sb.append("    }\n");
        sb.append("}");

        return sb.toString();
    }

    private static String generatePHP(String header, String body, String fnName, List<TestCase> tests) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?php\n\n");
        // Extract function parameters from header
        String params = "";
        if (header != null && !header.isEmpty()) {
            int paramStart = header.indexOf('(');
            int paramEnd = header.lastIndexOf(')');
            if (paramStart != -1 && paramEnd != -1 && paramEnd > paramStart) {
                params = header.substring(paramStart + 1, paramEnd).trim();
            }
        }

        // Define the function
        sb.append("function ").append(fnName).append("(").append(params).append(") {\n");
        sb.append("    ").append(body).append("\n");
        sb.append("}\n\n");

        // Add test cases
        for (TestCase t : tests) {
            String argsList = t.getInput().stream()
                    .map(CodeGenerator::formatPHPLiteral)
                    .collect(Collectors.joining(", "));
            sb.append("echo ").append(fnName).append("(").append(argsList).append(") . \"\\n\";\n");
        }

        sb.append("\n?>");

        return sb.toString();
    }

    private static String formatJavaLiteral(Object val) {
        return (val instanceof String) ? "\"" + val + "\"" : val.toString();
    }

    private static String formatPythonLiteral(Object val) {
        return (val instanceof String) ? "\"" + val + "\"" : val.toString();
    }

    private static String formatJavaScriptLiteral(Object val) {
        return (val instanceof String) ? "\"" + val + "\"" : val.toString();
    }

    private static String formatCSharpLiteral(Object val) {
        if (val instanceof String) {
            return "\"" + val + "\"";
        } else if (val instanceof Boolean) {
            return val.toString().toLowerCase(); // C# uses lowercase true/false
        } else {
            return val.toString();
        }
    }

    private static String formatPHPLiteral(Object val) {
        if (val instanceof String) {
            return "\"" + val + "\"";
        } else if (val instanceof Boolean) {
            return val.toString().toLowerCase(); // PHP uses lowercase true/false
        } else {
            return val.toString();
        }
    }

    public static ExtractedFunction extractParts(String fullFunction, String language) {
        fullFunction = fullFunction.trim();

        if (language.equalsIgnoreCase("python")) {
            int start = fullFunction.indexOf("def ") + 4;
            int end = fullFunction.indexOf('(', start);
            if (start == 3 || end == -1 || end <= start) {
                throw new IllegalArgumentException("Invalid Python function definition.");
            }
            String fnName = fullFunction.substring(start, end).trim();
            return new ExtractedFunction("", fullFunction, fnName);
        } else if (language.equalsIgnoreCase("javascript") || language.equalsIgnoreCase("js")) {
            // Handle function keyword, arrow functions, and method definitions in JavaScript
            String fnName;
            String header = "";
            String body = "";

            if (fullFunction.contains("function ")) {
                // Regular function
                int start = fullFunction.indexOf("function ") + 9;
                int end = fullFunction.indexOf('(', start);
                if (end == -1) {
                    throw new IllegalArgumentException("Invalid JavaScript function definition.");
                }
                fnName = fullFunction.substring(start, end).trim();

                int braceIdx = fullFunction.indexOf('{');
                if (braceIdx != -1) {
                    header = fullFunction.substring(0, braceIdx).trim();

                    // Extract body between braces
                    int closeBraceIdx = findMatchingCloseBrace(fullFunction, braceIdx);
                    if (closeBraceIdx != -1) {
                        body = fullFunction.substring(braceIdx + 1, closeBraceIdx).trim();
                    } else {
                        body = fullFunction.substring(braceIdx + 1).trim();
                    }
                }
            } else if (fullFunction.contains("=>")) {
                // Arrow function
                int arrowIdx = fullFunction.indexOf("=>");

                // Get everything before the arrow for header
                header = fullFunction.substring(0, arrowIdx).trim();

                // Extract function name from header (if any)
                int equalsIdx = header.indexOf('=');
                if (equalsIdx != -1) {
                    fnName = header.substring(0, equalsIdx).trim();
                } else {
                    fnName = "arrowFunction"; // Default name
                }

                // Get body after arrow
                body = fullFunction.substring(arrowIdx + 2).trim();
                if (body.startsWith("{") && body.endsWith("}")) {
                    body = body.substring(1, body.length() - 1).trim();
                }
            } else {
                // Method definition or other format
                int parenIdx = fullFunction.indexOf('(');
                if (parenIdx == -1) {
                    throw new IllegalArgumentException("Invalid JavaScript function definition.");
                }

                fnName = fullFunction.substring(0, parenIdx).trim();

                int braceIdx = fullFunction.indexOf('{');
                if (braceIdx != -1) {
                    header = fullFunction.substring(0, braceIdx).trim();

                    // Extract body between braces
                    int closeBraceIdx = findMatchingCloseBrace(fullFunction, braceIdx);
                    if (closeBraceIdx != -1) {
                        body = fullFunction.substring(braceIdx + 1, closeBraceIdx).trim();
                    } else {
                        body = fullFunction.substring(braceIdx + 1).trim();
                    }
                }
            }

            return new ExtractedFunction(header, body, fnName);
        } else if (language.equalsIgnoreCase("php")) {
            // PHP function
            int start = fullFunction.indexOf("function ") + 9;
            int end = fullFunction.indexOf('(', start);
            if (start == 8 || end == -1 || end <= start) {
                throw new IllegalArgumentException("Invalid PHP function definition.");
            }

            String fnName = fullFunction.substring(start, end).trim();

            int braceIdx = fullFunction.indexOf('{');
            String header = "";
            String body = "";

            if (braceIdx != -1) {
                header = fullFunction.substring(0, braceIdx).trim();

                // Extract body between braces
                int closeBraceIdx = findMatchingCloseBrace(fullFunction, braceIdx);
                if (closeBraceIdx != -1) {
                    body = fullFunction.substring(braceIdx + 1, closeBraceIdx).trim();
                } else {
                    body = fullFunction.substring(braceIdx + 1).trim();
                }
            }

            return new ExtractedFunction(header, body, fnName);
        }

        // Java, C, C++
        int parenIdx = fullFunction.indexOf('(');
        if (parenIdx == -1 || parenIdx <= 0) {
            throw new IllegalArgumentException("Invalid function definition (missing '(').");
        }

        // Look for the opening brace
        int braceIdx = fullFunction.indexOf('{');

        // If no opening brace, assume it's just a method signature
        if (braceIdx == -1) {
            String header = fullFunction.trim();
            int lastSpaceIdx = header.lastIndexOf(' ', parenIdx);
            if (lastSpaceIdx == -1 || lastSpaceIdx >= parenIdx) {
                throw new IllegalArgumentException("Unable to extract function name from header.");
            }
            String fnName = header.substring(lastSpaceIdx + 1, parenIdx).trim();
            return new ExtractedFunction(header, "", fnName);
        }

        // Extract the header (everything before the opening brace)
        String header = fullFunction.substring(0, braceIdx).trim();

        // Find the matching closing brace for the body
        int closeBraceIdx = findMatchingCloseBrace(fullFunction, braceIdx);

        String body;
        if (closeBraceIdx != -1) {
            // Extract just the implementation inside the braces
            body = fullFunction.substring(braceIdx + 1, closeBraceIdx).trim();
        } else {
            // If we can't find a proper closing brace, take everything after the opening brace
            body = fullFunction.substring(braceIdx + 1).trim();
        }

        // Extract the function name from the header
        int lastSpaceIdx = header.lastIndexOf(' ', parenIdx);
        if (lastSpaceIdx == -1 || lastSpaceIdx >= parenIdx) {
            throw new IllegalArgumentException("Unable to extract function name from header.");
        }

        String fnName = header.substring(lastSpaceIdx + 1, parenIdx).trim();
        return new ExtractedFunction(header, body, fnName);
    }

    public static class ExtractedFunction {
        public final String header;
        public final String body;
        public final String fnName;

        public ExtractedFunction(String header, String body, String fnName) {
            this.header = header;
            this.body = body;
            this.fnName = fnName;
        }
    }
}