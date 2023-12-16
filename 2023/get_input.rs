use std::env;
use std::process::Command;

fn main() {
    let args: Vec<String> = env::args().collect();

    let mut year = "2023";
    let mut day = "1";

    if args.len() > 2 {
        year = &args[1];
        day = &args[2];
    }

    let url = format!("https://adventofcode.com/{}/day/{}/input", year, day);
    let key = "AOC_SESSION";
    let aoc_session = env::var(key).expect("AOC_SESSION is not set!");
    let user_agent = "get_input.rs by yurachistic@gmail.com";

    let output = Command::new("curl")
        .arg("--cookie")
        .arg(format!("session={}", aoc_session))
        .arg("-A")
        .arg(user_agent)
        .arg(url)
        .output()
        .expect("failed to execute curl");

    // Check if the curl command was successful
    if output.status.success() {
        let response_body =
            String::from_utf8(output.stdout).expect("failed to convert output to String");
        println!("{}", response_body);
    } else {
        let error_message =
            String::from_utf8(output.stderr).expect("failed to convert error output to String");
        panic!("Error: {}", error_message);
    }
}
