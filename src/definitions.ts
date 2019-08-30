declare module "@capacitor/core" {
  interface PluginRegistry {
    BaiduLocation: BaiduLocationPlugin;
  }
}

export interface BaiduLocationPlugin {
  echo(options: { value: string }): Promise<{value: string}>;
}
