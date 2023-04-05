import 'package:flutter/material.dart';
import 'package:flutter_notif_on_kill_poc/notif_on_kill.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Notif on app kill',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static bool value = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Notif on app kill')),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Center(child: Text("Show notification on app kill")),
          Switch(
            value: value,
            onChanged: (bool _) {
              setState(() => value = _);
              NotifOnKill.toggleNotifOnKill(value);
            },
          ),
        ],
      ),
    );
  }
}
