import { WebPlugin } from "@capacitor/core";
export class FilePickerWeb extends WebPlugin {
    constructor() {
        super({
            name: "FilePicker",
            platforms: ["web"],
        });
    }
    showFilePicker(_options) {
        return Promise.reject("Method not implemented");
    }
}
//# sourceMappingURL=web.js.map