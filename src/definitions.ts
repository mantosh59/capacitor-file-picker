export class FiletTypes {
  static IMAGE = "image"; // For any type of image file
  static DOC = "doc"; // For raw files
  static VIDEO = "video"; // For any type of video files
}

export interface FilePickerResult {
  uri: string;
  name: string;
  mimeType: string;
  extension: string;
  size: number;
}

export interface FilePickerPlugin {
  showFilePicker(options?: { fileTypes?: string[] }): Promise<FilePickerResult>;
}
