import { WebPlugin } from '@capacitor/core';
import { BaiduLocationPlugin } from './definitions';
export declare class BaiduLocationWeb extends WebPlugin implements BaiduLocationPlugin {
    constructor();
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
declare const BaiduLocation: BaiduLocationWeb;
export { BaiduLocation };
