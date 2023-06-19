import 'package:flutter/services.dart';

const platform = MethodChannel('com.example/channel');
void callNativeMethod() async {
  try {
    final result = await platform.invokeMethod('methodName', 'Edson');
    print('Resultado do método nativo: $result');
  } on PlatformException catch (error) {
    print('Erro ao chamar o método nativo: $error');
  }
}
