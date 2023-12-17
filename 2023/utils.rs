use std::fs::File;
use std::io::{BufRead, BufReader};

pub fn read_input(file_path: &str) -> Vec<String> {
    // Open the file
    let file = File::open(file_path).unwrap();

    // Create a buffered reader
    let reader = BufReader::new(file);

    reader
        .lines()
        .filter_map(Result::ok)
        .map(|line| line.trim().to_string())
        .filter(|line| !line.is_empty())
        .collect()
}