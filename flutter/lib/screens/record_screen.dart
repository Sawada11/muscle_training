import 'package:flutter/material.dart';
import '../models/record.dart';
import '../services/record_service.dart';
import '../utils/token_storage.dart';
import 'login_screen.dart';

class RecordScreen extends StatefulWidget {
  const RecordScreen({super.key});

  @override
  State<RecordScreen> createState() => _RecordScreenState();
}

class _RecordScreenState extends State<RecordScreen> {
  late Future<List<Record>> _recordsFuture;

  final _formKey = GlobalKey<FormState>();
  final _exerciseController = TextEditingController();
  final _weightController = TextEditingController();
  final _repsController = TextEditingController();
  final _setsController = TextEditingController();

  int? _editingId;

  @override
  void initState() {
    super.initState();
    _loadRecords();
  }

  void _loadRecords() {
    setState(() {
      _recordsFuture = RecordService.fetchRecords();
    });
  }

  Future<void> _submitRecord() async {
    if (_formKey.currentState!.validate()) {
      final success = (_editingId == null)
          ? await RecordService.addRecord(
              exercise: _exerciseController.text,
              weight: int.parse(_weightController.text),
              reps: int.parse(_repsController.text),
              sets: int.parse(_setsController.text),
            )
          : await RecordService.updateRecord(
              id: _editingId!,
              exercise: _exerciseController.text,
              weight: int.parse(_weightController.text),
              reps: int.parse(_repsController.text),
              sets: int.parse(_setsController.text),
            );

      final msg = _editingId == null ? '追加' : '更新';

      if (success) {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('✅ 記録を$msgしました')));
        _clearForm();
        _loadRecords();
      } else {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text('❌ 記録の$msgに失敗しました')));
      }
    }
  }

  void _editRecord(Record r) {
    setState(() {
      _editingId = r.id;
      _exerciseController.text = r.exercise;
      _weightController.text = r.weight.toString();
      _repsController.text = r.reps.toString();
      _setsController.text = r.sets.toString();
    });
  }

  void _clearForm() {
    _editingId = null;
    _exerciseController.clear();
    _weightController.clear();
    _repsController.clear();
    _setsController.clear();
  }

  Future<void> _deleteRecord(int id) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (_) => AlertDialog(
        title: const Text('削除確認'),
        content: const Text('この記録を削除してよろしいですか？'),
        actions: [
          TextButton(onPressed: () => Navigator.pop(context, false), child: const Text('キャンセル')),
          TextButton(onPressed: () => Navigator.pop(context, true), child: const Text('削除')),
        ],
      ),
    );

    if (confirmed == true) {
      final success = await RecordService.deleteRecord(id);
      if (success) {
        ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('🗑 記録を削除しました')));
        _loadRecords();
      }
    }
  }

  Future<void> _logout() async {
    await TokenStorage().clearToken(); // ✅ 修正済み（インスタンス経由で呼び出し）
    if (context.mounted) {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (_) => const LoginScreen()),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('筋トレ記録'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: _logout,
            tooltip: 'ログアウト',
          ),
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Form(
              key: _formKey,
              child: Column(
                children: [
                  TextFormField(
                    controller: _exerciseController,
                    decoration: const InputDecoration(labelText: '種目'),
                    validator: (v) => v == null || v.isEmpty ? '必須項目です' : null,
                  ),
                  TextFormField(
                    controller: _weightController,
                    decoration: const InputDecoration(labelText: '重量(kg)'),
                    keyboardType: TextInputType.number,
                    validator: (v) => v == null || int.tryParse(v) == null ? '数字を入力' : null,
                  ),
                  TextFormField(
                    controller: _repsController,
                    decoration: const InputDecoration(labelText: '回数'),
                    keyboardType: TextInputType.number,
                    validator: (v) => v == null || int.tryParse(v) == null ? '数字を入力' : null,
                  ),
                  TextFormField(
                    controller: _setsController,
                    decoration: const InputDecoration(labelText: 'セット数'),
                    keyboardType: TextInputType.number,
                    validator: (v) => v == null || int.tryParse(v) == null ? '数字を入力' : null,
                  ),
                  const SizedBox(height: 12),
                  Row(
                    children: [
                      ElevatedButton(
                        onPressed: _submitRecord,
                        child: Text(_editingId == null ? '記録を追加' : '記録を更新'),
                      ),
                      const SizedBox(width: 12),
                      if (_editingId != null)
                        TextButton(
                          onPressed: _clearForm,
                          child: const Text('キャンセル'),
                        ),
                    ],
                  ),
                ],
              ),
            ),
            const SizedBox(height: 24),
            FutureBuilder<List<Record>>(
              future: _recordsFuture,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const CircularProgressIndicator();
                }
                if (!snapshot.hasData || snapshot.data!.isEmpty) {
                  return const Text('記録がありません');
                }

                final records = snapshot.data!;
                return ListView.builder(
                  shrinkWrap: true,
                  physics: const NeverScrollableScrollPhysics(),
                  itemCount: records.length,
                  itemBuilder: (context, index) {
                    final r = records[index];
                    return ListTile(
                      title: Text('${r.exercise}（${r.date}）'),
                      subtitle: Text('重量: ${r.weight}kg × ${r.reps}回 × ${r.sets}セット'),
                      trailing: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          IconButton(icon: const Icon(Icons.edit), onPressed: () => _editRecord(r)),
                          IconButton(icon: const Icon(Icons.delete), onPressed: () => _deleteRecord(r.id)),
                        ],
                      ),
                    );
                  },
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
