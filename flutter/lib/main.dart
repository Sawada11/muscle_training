import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'firebase_options.dart'; // ✅ 追加されたファイルをimport
import 'screens/login_screen.dart';
import 'screens/record_screen.dart';
import 'services/api_service.dart'; // dio初期化用

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  // ✅ Webかそれ以外で初期化方法を分ける
  await Firebase.initializeApp(
    options: kIsWeb ? DefaultFirebaseOptions.web : DefaultFirebaseOptions.currentPlatform,
  );

  final prefs = await SharedPreferences.getInstance();
  final token = prefs.getString('jwt_token');
  runApp(MyApp(isLoggedIn: token != null));
}

class MyApp extends StatelessWidget {
  final bool isLoggedIn;
  const MyApp({super.key, required this.isLoggedIn});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '筋トレ記録アプリ',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: isLoggedIn ? const RecordScreen() : const LoginScreen(),
    );
  }
}
