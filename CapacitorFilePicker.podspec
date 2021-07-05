
  Pod::Spec.new do |s|
    s.name = 'CapacitorFilePicker'
    s.version = '1.0.0'
    s.summary = 'This plugin presents the native UI for picking a file.'
    s.license = 'MIT'
    s.homepage = 'https://github.com/mantosh59/capacitor-file-picker'
    s.author = 'XELIT'
    s.source = { :git => 'https://github.com/mantosh59/capacitor-file-picker', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '12.0'
    s.dependency 'Capacitor'
  end