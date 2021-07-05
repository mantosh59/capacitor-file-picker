# Capacitor File Picker

## Description

Plugin to pick files/images/videos from android or ios.

## Installation

- `npm i filpicker-updated`
## Usage

import { FilePicker } from "capacitor-file-picker";

const { FilePicker } = Plugins;

FilePicker.showFilePicker({
  fileTypes: ["image/*", "application/pdf"],
}).then(
  (fileResult: FilePickerResult) => {
    const fileUri = fileResult.uri;
    const fileName = fileResult.name;
    const fileMimeType = fileResult.mimeType;
    const fileExtension = fileResult.extension;
  },
  (error) => {
    console.log(error);
  }
);
```
