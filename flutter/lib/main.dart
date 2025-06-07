import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '筋トレ記録アプリ',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      home: const MyHomePage(title: '筋トレ記録送信'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  // ✅ APIにPOSTする関数
  Future<void> addRecord() async {
    const String token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.abc123.def456';
    const String apiUrl = 'http://127.0.0.1:8080/records';


    final response = await http.post(
      Uri.parse(apiUrl),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
      body: jsonEncode({
        'date': '2025-05-29',
        'exercise': 'スクワット',
        'weight': 80,
        'reps': 10,
        'sets': 3,
        'memo': 'Flutterから送信テスト'
      }),
    );

    if (response.statusCode == 200) {
      print('保存成功: ${response.body}');
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('記録を送信しました！')),
      );
    } else {
      print('エラー: ${response.statusCode}, ${response.body}');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('送信失敗: ${response.statusCode}')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: addRecord,
          child: const Text('記録を送信'),
        ),
      ),
    );
  }
}
