
  Pod::Spec.new do |s|
    s.name = 'CapacitorBrainyBaidulocation'
    s.version = '0.0.1'
    s.summary = 'Capacitor百度定位插件'
    s.license = 'MIT'
    s.homepage = 'https://github.com/DaiHuaXieHuaKai/capacitor-brainy-baidulocation.git'
    s.author = 'Brainy'
    s.source = { :git => 'https://github.com/DaiHuaXieHuaKai/capacitor-brainy-baidulocation.git', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end