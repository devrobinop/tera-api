
/*
Project Setup (CMakeLists.txt):
You would typically use a package manager like vcpkg or Conan to install these libraries. A simple CMakeLists.txt might look like this:
  cmake_minimum_required(VERSION 3.15)
project(TeraboxApiExample)

# Find CPR and nlohmann/json (assumes they are installed)
find_package(cpr REQUIRED)
find_package(nlohmann_json REQUIRED)

add_executable(main main.cpp)

target_link_libraries(main PRIVATE cpr::cpr nlohmann_json::nlohmann_json)
*/



#include <iostream>
#include <string>
#include <cpr/cpr.h> // For HTTP requests
#include <nlohmann/json.hpp> // For JSON parsing

// Use the nlohmann namespace for convenience
using json = nlohmann::json;

int main() {
    // The Terabox URL you want to process
    std::string teraboxShareUrl = "https://1024terabox.com/s/1bNLoEdlmOuyZcofBcnFdow";

    // 1. Construct the full API endpoint URL
    std::string apiUrl = "https://terabox-worker.robinkumarshakya103.workers.dev/api";
    
    std::cout << "Fetching data from API..." << std::endl;

    // 2. Make the GET request using cpr, passing the URL as a parameter
    cpr::Response response = cpr::Get(cpr::Url{apiUrl},
                                      cpr::Parameters{{"url", teraboxShareUrl}});

    // 3. Check if the request was successful
    if (response.status_code == 200) {
        try {
            // 4. Parse the JSON response
            json data = json::parse(response.text);

            if (data["success"]) {
                if (data.contains("files") && !data["files"].empty()) {
                    auto firstFile = data["files"][0]; // Get the first file object

                    std::cout << "\n--- File Details ---" << std::endl;
                    std::cout << "File Name: " << firstFile["file_name"].get<std::string>() << std::endl;
                    std::cout << "Size: " << firstFile["size"].get<std::string>() << std::endl;
                    std::cout << "Download Link: " << firstFile["download_url"].get<std::string>() << std::endl;
                    std::cout << "Streaming Link: " << firstFile["streaming_url"].get<std::string>() << std::endl;
                    std::cout << "--------------------" << std::endl;
                }
            } else {
                std::cerr << "API Error: " << data["error"].get<std::string>() << std::endl;
            }
        } catch (const json::parse_error& e) {
            std::cerr << "Failed to parse JSON: " << e.what() << std::endl;
        }
    } else {
        std::cerr << "HTTP Error: " << response.status_code << std::endl;
        std::cerr << "Response: " << response.text << std::endl;
    }

    return 0;
}
