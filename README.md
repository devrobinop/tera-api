
# Terabox Direct Link API
# Demo
https://terabox-worker.robinkumarshakya103.workers.dev/





![API Status](https://img.shields.io/badge/API_Status-Online-brightgreen)
![License](https://img.shields.io/badge/License-Free_to_Use-blue)
![Cloudflare](https://img.shields.io/badge/Powered%20by-Cloudflare%20Workers-orange)

A free, public API for generating high-quality, direct download and streaming links from any Terabox share URL. This service is built on Cloudflare's global edge network, ensuring high speed and reliability for developers and their applications.

---

## 🌐 Live API Endpoint

**Base URL:** https://terabox-worker.robinkumarshakya103.workers.dev/


## 🛠️ API Endpoint Documentation

The API has one primary endpoint for generating links.

### `GET /api`

This endpoint processes a Terabox share link and returns all relevant file information and usable links.


#### Example Request
https://terabox-worker.robinkumarshakya103.workers.dev/api?url=https://1024terabox.com/s/1bNLoEdlmOuyZcofBcnFdow



#### ✅ Successful Response (`200 OK`)

The response is a JSON object containing credits and an array of file objects.

```json
{
  "success": true,
  "credits": {
    "developer": "Robin Shakya",
    "email": "dev.robinop@gmail.com",
    "telegram": "https://t.me/ItS_Me_Shakya"
  },
  "files": [
    {
      "file_name": "My_Movie_File.mkv",
      "size": "724.52 MB",
      "uploader_name": "SomeUploader",
      "download_url": "https://terabox-worker.robinkumarshakya103.workers.dev/download?dlink=...",
      "streaming_url": "https://terabox-worker.robinkumarshakya103.workers.dev/stream?dlink=...",
      "original_download_url": "https://d8.freeterabox.com/file/..."
    }
  ]
}
```

# How to Use the Links
 ` download_url : `
 A safe, proxied link that forces a download. Use this for user-facing download buttons.
 
` streaming_url: `
A link that provides a video player for reliable streaming.

 `original_download_url:` 
 The raw, direct link from Terabox. May fail if used directly from a browser due to Referer restrictions but is useful for server-to-server transfers.

 
# ❌ Error Response (400 or 500)
If the request fails, the response will clearly state the reason.
```json
{
  "success": false,
  "error": "Failed to get share info: Unknown error"
}
```
# 💻 Code Example: 
How to Use the API
Here is a simple example of how to use the API in JavaScript.


```
async function getTeraboxLink(teraboxShareUrl) {
  try {
    const apiUrl = `https://terabox-worker.robinkumarshakya103.workers.dev/api?url=${encodeURIComponent(teraboxShareUrl)}`;
    
    const response = await fetch(apiUrl);
    const data = await response.json();

    if (data.success && data.files.length > 0) {
      const file = data.files[0]; // Get the first file in the response
      console.log("File Name:", file.file_name);
      console.log("Download Link:", file.download_url);
      console.log("Streaming Link:", file.streaming_url);
    } else {
      console.error("API Error:", data.error);
    }
  } catch (error) {
    console.error("Fetch Error:", error);
  }
}

// Example usage:
const myTeraboxLink = "https://1024terabox.com/s/1bNLoEdlmOuyZcofBcnFdow";
getTeraboxLink(myTeraboxLink);

```

# Developer Contact
Name: Robin Shakya
Email: dev.robinop@gmail.com
Telegram: @ItS_Me_Shakya (Get in touch for the original script)
