import 'package:flutter/material.dart';
import '../services/api.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});
  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final _titleCtrl = TextEditingController();
  List<dynamic> _topics = [];
  bool _loading = false;

  @override
  void initState() {
    super.initState();
    _load();
  }

  void _load() async {
    setState(() => _loading = true);
    final api = ApiService();
    final t = await api.getTopics();
    setState(() {
      _topics = t ?? [];
      _loading = false;
    });
  }

  void _create() async {
    final api = ApiService();
    final ok = await api.createTopic(_titleCtrl.text.trim());
    if (ok) {
      _titleCtrl.clear();
      _load();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Topics')),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: [
            Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _titleCtrl,
                    decoration: const InputDecoration(hintText: 'New topic'),
                  ),
                ),
                IconButton(icon: const Icon(Icons.add), onPressed: _create),
              ],
            ),
            Expanded(
              child: _loading
                  ? const Center(child: CircularProgressIndicator())
                  : ListView.builder(
                      itemCount: _topics.length,
                      itemBuilder: (_, i) {
                        final t = _topics[i];
                        return ListTile(title: Text(t['title'] ?? ''));
                      },
                    ),
            ),
          ],
        ),
      ),
    );
  }
}
