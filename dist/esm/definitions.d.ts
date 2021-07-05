export declare class FiletTypes {
    static IMAGE: string;
    static DOC: string;
    static VIDEO: string;
}
export interface FilePickerResult {
    uri: string;
    name: string;
    mimeType: string;
    extension: string;
    size: number;
}
export interface FilePickerPlugin {
    showFilePicker(options?: {
        fileTypes?: string[];
    }): Promise<FilePickerResult>;
}
