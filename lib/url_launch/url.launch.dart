import 'package:flutter/services.dart';
import 'package:native_teste/url_launch/url_launch_exception.dart';

class UrlLaunch {
  static const platform = MethodChannel('com.example/channel');

  Future<void> call(
    String url,
  ) async {
    try {
      await platform.invokeMethod('urlLaunch', url);
    } on PlatformException catch (error) {
      throw UrlLaunchException(message: error.message ?? 'Error inexpected');
    }
  }
}
