import { WebPlugin } from '@capacitor/core';
import { BaiduLocationPlugin } from './definitions';

export class BaiduLocationWeb extends WebPlugin implements BaiduLocationPlugin {
  constructor() {
    super({
      name: 'BaiduLocation',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }
}

const BaiduLocation = new BaiduLocationWeb();

export { BaiduLocation };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(BaiduLocation);
