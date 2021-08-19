//
//  ViewController.swift
//  SE1Challenge
//
//  Created by Michal Miko on 18.08.2021.
//

import UIKit

final class SE1ChallengeViewController: UIViewController {
    let viewModel: Orderable
    private let formatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.currencyCode = "CZK"
        formatter.maximumFractionDigits = 2
        return formatter
    }()
    
    let btnRun: UIButton = {
        let button = UIButton(type: .custom)
        button.setTitle("Run", for: .normal)
        button.contentEdgeInsets = .init(top: 10, left: 30, bottom: 10, right: 30)
        button.setTitleColor(.white, for: .normal)
        button.backgroundColor = .systemBlue
        return button
    }()
    let lblResult: UILabel = {
        let label = UILabel()
        label.textAlignment = .center
        label.text = "Result"
        return label
    }()
    
    override func loadView() {
        super.loadView()
        let view = UIView()
        view.backgroundColor = .systemBackground
        self.view = view
    }
    
    init(viewModel: Orderable) {
        self.viewModel = viewModel
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
    }
    
    private func setupLayout() {
        [btnRun, lblResult].forEach({
            $0.translatesAutoresizingMaskIntoConstraints = false
            self.view.addSubview($0)
        })
        
        btnRun.addTarget(self, action: #selector(runAction), for: .touchUpInside)
        btnRun.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        btnRun.bottomAnchor.constraint(equalTo: view.centerYAnchor, constant: -10).isActive = true
        lblResult.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 20).isActive = true
        lblResult.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -20).isActive = true
        lblResult.topAnchor.constraint(equalTo: view.centerYAnchor, constant: 10).isActive = true
    }
    
    @objc func runAction() {
        viewModel.generate(completion: { [weak self] (result) in
            switch result {
            case .cacheLoaded(let price):
                let priceString: String = self?.formatter.string(from: NSNumber(value: price.price)) ?? "????"
                self?.lblResult.text = "Price loaded from cache is \(priceString)"
            case .priceGenerated(let price):
                let priceString: String = self?.formatter.string(from: NSNumber(value: price.price)) ?? "????"
                self?.lblResult.text = "Price for new order is \(priceString)"
            case .priceFailed:
                self?.lblResult.text = "Price generation failed"
            }
        })
    }
}
