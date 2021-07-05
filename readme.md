# Capacitor File Picker

## Description

Plugin to pick files/images/videos from android or ios.

## Installation

- `npm i filpicker-updated`
## Usage

import { FilePicker } from "filpicker-updated/dist/esm"; 

FilePicker.showFilePicker({
  fileTypes: ["image/*", "video/*"],
}).then(
  (fileResult: FilePickerResult) => {
    const fileUri = fileResult.uri;
    const fileName = fileResult.name;
    const fileMimeType = fileResult.mimeType;
    const fileExtension = fileResult.extension;
    const fileSize = fileResult.size;
  },
  (error) => {
    console.log(error);
  }
);
```
